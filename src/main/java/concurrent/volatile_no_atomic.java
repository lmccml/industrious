package concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class volatile_no_atomic {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch ct = new CountDownLatch(3);
        ExecutorService es = Executors.newFixedThreadPool(5);
        es.submit(new runnable_first(ct));
        es.submit(new runnable_second(ct));
        ct.await();
        System.out.println("任务处理完了");
    }
}
