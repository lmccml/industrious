package concurrent;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author lmc
 * @date 2020/3/19 9:06
 */
public class QueueTest {
    static LinkedTransferQueue<String> linkedTransferQueue = new LinkedTransferQueue<String>();

    public static void main(String[] args) throws Exception {
        /*ArrayBlockingQueue是一个用数组实现的有界阻塞队列。
        此队列按照先进先出（FIFO）的原则对元素进行排序。
        */
        //公平的阻塞队列
        ArrayBlockingQueue fairQueue = new ArrayBlockingQueue(1000, true);

        /*
        LinkedBlockingQueue是一个用链表实现的有界阻塞队列。此队列的
        默认和最大长度为Integer.MAX_VALUE。此队列按照先进先出的原则
        对元素进行排序。
         */
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();


        /*
        PriorityBlockingQueue是一个支持优先级的无界阻塞队列。默认情
        况下元素采取自然顺序升序排列。也可以自定义类实现compareTo()方
        法来指定元素排序规则，或者初始化PriorityBlockingQueue时，指定构
        造参数Comparator来对元素进行排序。
        需要注意的是不能保证同优先级元素的顺序。
         */
        PriorityBlockingQueue<Integer> priorityBlockingQueue = new PriorityBlockingQueue(10, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });

        /*
        DelayQueue是一个支持延时获取元素的无界阻塞队列。队列使用
        PriorityQueue来实现。队列中的元素必须实现Delayed接口，在创建元素
        时可以指定多久才能从队列中获取当前元素。只有在延迟期满时才能从
        队列中提取元素。
         */
//        DelayQueue<DelayedTask> tasks =
//                Stream.concat( // Random delays:
//                        new Random(47).ints(20, 0, 4000)
//                                .mapToObj(DelayedTask::new),
//                        // Add the summarizing task:
//                        Stream.of(new DelayedTask.EndTask(4000)))
//                        .collect(Collectors
//                                .toCollection(DelayQueue::new));
//        while(tasks.size() > 0) {
//            tasks.take().run();
//        }

        /*
        SynchronousQueue是一个不存储元素的阻塞队列。每一个put操作必
        须等待一个take操作，否则不能继续添加元素。
        SynchronousQueue可以看成是一个传球手，负责把生产者线程处理
        的数据直接传递给消费者线程。队列本身并不存储任何元素，非常适合
        传递性场景。SynchronousQueue的吞吐量高于LinkedBlockingQueue和ArrayBlockingQueue。
        */
        SynchronousQueue synchronousQueue = new SynchronousQueue();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Timer timer = new Timer();
                TimerTask timerTask2 = new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            synchronousQueue.put(Integer.valueOf(new Random().nextInt(10)));
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                    }
                };
                timer.scheduleAtFixedRate(timerTask2, 1000, 1000); //延迟1秒，每秒执行一次
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //peek为null
                    //synchronousQueue.peek();
                    Integer temp;
                    while (null != (temp = (Integer) synchronousQueue.take())) {
                        System.out.println(temp);
                    }
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        }).start();

        /*
       LinkedTransferQueue 一个基于链表的无边界阻塞队列。这个队列按照生产者FIFO（先进先出）的顺序提供元素。
       队列的头节点是等待时间最长的生产者，队尾是等待时间最短的生产者。
       它有个特殊的方法 transfer，若调用时有消费者等待，则直接匹配将元素转移给消费者，若没有消费者等待则自旋后阻塞。
       LinkedTransferQueue 可以看做是 LinkedBlockingQueue 和 SynchronousQueue 的结合
       LinkedBlockingQueue 能够存储元素，但是内部使用了大量的锁，并发效率不高。
       SynchronousQueue 内部不能存储元素，可能会导致大量生产者阻塞。
        */
        ExecutorService exService = Executors.newFixedThreadPool(2);
        Producer producer = new QueueTest().new Producer();
        Consumer consumer = new QueueTest().new Consumer();
        exService.execute(producer);
        exService.execute(consumer);
        exService.shutdown();

        /*
        LinkedBlockingDeque是一个由链表结构组成的双向阻塞队列。所谓双向队列指的是可以从队列的两端插入和移出元素。
         */
        LinkedBlockingDeque<Work> deque = new LinkedBlockingDeque<Work>();
        LinkedBlockingDeque<Work> other = new LinkedBlockingDeque<Work>();
        new Thread(new ConsumerAndProducer(deque,other)).start();
        new Thread(new ConsumerAndProducer(deque,other)).start();
        new Thread(new ConsumerAndProducer(other,deque)).start();
        new Thread(new ConsumerAndProducer(other,deque)).start();

    }

    class Producer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                try {
                    System.out.println("Producer is waiting to transfer...");
                    linkedTransferQueue.transfer("A" + i);
                    System.out.println("producer transfered element: A" + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Consumer implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                try {
                    System.out.println("Consumer is waiting to take element...");
                    String s = linkedTransferQueue.take();
                    System.out.println("Consumer received Element: " + s);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    private static class Work implements Runnable{
        private static Object object = new Object();
        private static int count=0;
        public final int id;
        private long putThread;
        public Work(){
            synchronized(object){
                id=count++;
            }
        }
        @Override
        public void run() {
            if(Thread.currentThread().getId()!=putThread){
                System.out.println("===================================================");
            }
            System.out.println(Thread.currentThread().getId()+":"+putThread+"// finish job "+id);

        }
        public long getPutThread() {
            return putThread;
        }
        public void setPutThread(long putThread) {
            this.putThread = putThread;
        }

    }

    public static Work generateWork(){
        return new Work();
    }

    private static class ConsumerAndProducer implements Runnable{
        private Random random=new Random();
        private final LinkedBlockingDeque<Work> deque;
        private final LinkedBlockingDeque<Work> otherWork;
        public ConsumerAndProducer(LinkedBlockingDeque<Work> deque, LinkedBlockingDeque<Work> otherWork){
            this.deque=deque;
            this.otherWork=otherWork;
        }
        @Override
        public void run() {
            while(!Thread.interrupted()){
                try {
                    Thread.sleep(200);
                    if(random.nextBoolean()){
                        int count=random.nextInt(5);
                        for(int i=0;i<count;i++){
                            Work w = generateWork();
                            w.setPutThread(Thread.currentThread().getId());
                            deque.putLast(w);
                        }
                    }
                    if(deque.isEmpty()){
                        if(!otherWork.isEmpty()){
                            otherWork.takeLast().run();;
                        }

                    }else{
                        deque.takeFirst().run();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
