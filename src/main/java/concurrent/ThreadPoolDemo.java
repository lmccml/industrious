package concurrent;

import java.util.concurrent.*;

public class ThreadPoolDemo {
    public static void main(String[] args) {
        RejectedExecutionHandler defaultHandler = new ThreadPoolExecutor.AbortPolicy();
        ExecutorService executorService = new ThreadPoolExecutor(
                5,
                5,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                Executors.defaultThreadFactory(),
                defaultHandler
                );
        Executor fixed_threadpool = Executors.newFixedThreadPool(3);
        Executor single_thread_executor = Executors.newSingleThreadExecutor();
        Executor work_stealingpool = Executors.newWorkStealingPool(3);
        Executor scheduled_threadpool = Executors.newScheduledThreadPool(3);
        fixed_threadpool.execute(new test_runnable());


    }
    public static class test_runnable implements Runnable{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getId());
        }
    }
}
