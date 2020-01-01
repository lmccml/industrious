package concurrent;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Condition;

/**
 * @author lmc
 * @date 2019/12/31
 */
public class ConsumerProducerModelTest {
    final static CustomAQSLock customAQSLock = new CustomAQSLock();
    final static Condition emptyCondition = customAQSLock.newCondition();
    final static Condition fullCondition = customAQSLock.newCondition();
    final static Queue<Integer> queue = new LinkedBlockingQueue<>();
    final static int queueSize = 10;
    static int count = 0;

    public static void main(String[] args) throws Exception{
        long start = System.currentTimeMillis();
        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                //获取独占锁
                customAQSLock.lock();
                try {
                    while (queue.size() < queueSize) {
                        queue.add(ThreadLocalRandom.current().nextInt(10));
                        count++;
                        if(System.currentTimeMillis() - start > 1000){
                            System.out.println(count);
                            return;
                        }
                        //唤醒消费者
                        emptyCondition.signalAll();
                        while (queue.size() == queueSize) {
                            fullCondition.await();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    customAQSLock.unlock();
                }
            }
        });


        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                //获取独占锁
                customAQSLock.lock();
                try {
                    while (queue.size() > 0) {
                        Integer integer = queue.poll();
                        System.out.println(integer);
                        //唤醒生产者
                        fullCondition.signalAll();
                        while (queue.size() == 0) {
                            emptyCondition.await();
                        }
                    }
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    customAQSLock.unlock();
                }
            }
        });

        Thread consumer2 = new Thread(new Runnable() {
            @Override
            public void run() {
                //获取独占锁
                customAQSLock.lock();
                try {
                    while (queue.size() > 0) {
                        Integer integer = queue.poll();
                        System.out.println(integer);
                        //唤醒生产者
                        fullCondition.signalAll();
                        while (queue.size() == 0) {
                            emptyCondition.await();
                        }
                    }
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    customAQSLock.unlock();
                }
            }
        });

        //启动线程
        producer.start();
        consumer.start();
        //consumer2.start();
    }

}
