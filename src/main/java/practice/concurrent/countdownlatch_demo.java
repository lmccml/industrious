package practice.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/*
CountDownLatch底层原理
CountDownLatch通过AQS（AbstractQueuedSynchronizer）里面的共享锁来实现的。
ReentrantLock也是使用AQS
 */
public class countdownlatch_demo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch ct = new CountDownLatch(3);
        ExecutorService es = Executors.newFixedThreadPool(5);
        es.submit(new runnable_first(ct));
        es.submit(new runnable_second(ct));
        es.submit(new runnable_second(ct));
        ct.await();
        System.out.println("任务处理完了");
    }
}
