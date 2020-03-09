package concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lmc
 * @date 2020/3/5 17:27
 */
public class ConsumerProducerModelTest {
    private static AtomicInteger num = new AtomicInteger(100);
    private static volatile boolean flag = true;
    public static void main(String[] args) throws Exception {
        ConsumerProducerModelTest consumerProducerModelTest = new ConsumerProducerModelTest();
        Thread threadProducer = new Thread(new Runnable() {
            @Override
            public void run() {
                consumerProducerModelTest.product();
            }
        });

        Thread threadProducer2 = new Thread(new Runnable() {
            @Override
            public void run() {
                consumerProducerModelTest.product();
            }
        });



        Thread threadConsumer = new Thread(new Runnable() {
            @Override
            public void run() {
                consumerProducerModelTest.consume();
            }
        });

        Thread threadConsumer2 = new Thread(new Runnable() {
            @Override
            public void run() {
                consumerProducerModelTest.consume();
            }
        });

        threadProducer.start();
        threadProducer2.start();
        threadConsumer.start();
        threadConsumer2.start();
        Thread.sleep(3000);
        flag = false;
        System.out.println("已经工作3秒了，该歇歇了！" + System.currentTimeMillis());

    }

    public void product() {
        while(flag) {
            synchronized (num) {
                while (num.get() >= 100) {
                    try {
                        num.wait();
                    } catch (Exception e) {
                        System.out.println("生产者" + Thread.currentThread().getName() + "被中断了");
                    }
                }
                //默认执行生产操作
                num.addAndGet(100);
                //通知消费者消费
                num.notifyAll();
            }
        }
    }

    public void consume() {
        while (flag) {
            synchronized (num) {
                try {
                    while (num.get() <= 0) {
                        num.wait();
                    }
                    //消费
                    num.decrementAndGet();
                    System.out.println(Thread.currentThread().getName() + "消费了！还剩" + num.get()+ System.currentTimeMillis());
                    num.notifyAll();
                } catch (Exception e) {
                    System.out.println("消费者" + Thread.currentThread().getName() + "被中断了");
                }
            }
        }
    }
}
