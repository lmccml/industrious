package concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lmc
 * @date 2020/3/6 10:50
 */
public class ConsumerProducerByBlockingQueue {
    public static void main(String[] args) {
        ConsumerProducerByBlockingQueue v3 = new ConsumerProducerByBlockingQueue();
        new Thread(()->{
            try {
                v3.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "AAA").start();

        new Thread(()->{
            try {
                v3.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "BBB").start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        v3.stop();
    }

    private static final int MAX_CAPACITY = 10; //阻塞队列容量
    private static BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(MAX_CAPACITY); //阻塞队列
    private volatile boolean FLAG = true;
    private AtomicInteger atomicInteger = new AtomicInteger();

    public void produce() throws InterruptedException {
        while (FLAG) {
            boolean retvalue = blockingQueue.offer(atomicInteger.incrementAndGet(), 2, TimeUnit.SECONDS);
            if (retvalue == true) {
                System.out.println(Thread.currentThread().getName() + "\t 插入队列" + atomicInteger.get() + "成功" + "资源队列大小= " + blockingQueue.size());
            } else {
                System.out.println(Thread.currentThread().getName() + "\t 插入队列" + atomicInteger.get() + "失败" + "资源队列大小= " + blockingQueue.size());

            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName() + "FLAG变为flase，生产停止");
    }

    public void consume() throws InterruptedException {
        Integer result = null;
        while (true) {
            result = blockingQueue.poll(2, TimeUnit.SECONDS);
            if (null == result) {
                System.out.println("超过两秒没有取道数据，消费者即将退出");
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t 消费" + result + "成功" + "\t\t" + "资源队列大小= " + blockingQueue.size());
            Thread.sleep(1500);
        }

    }

    public void stop() {
        this.FLAG = false;
    }
}


