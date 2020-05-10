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
        Executor cached_threadpool = Executors.newCachedThreadPool(); //就是一个多了缩减，少了加的灵活变通的线程池，用的是SynchronousQueue
        Executor fixed_threadpool = Executors.newFixedThreadPool(3); //用的是LinkedBlockingQueue
        Executor single_thread_executor = Executors.newSingleThreadExecutor(); //用的是LinkedBlockingQueue
        Executor work_stealingpool = Executors.newWorkStealingPool(3); //用的是LinkedBlockingQueue
        Executor scheduled_threadpool = Executors.newScheduledThreadPool(3); //用的是DelayedWorkQueue
        fixed_threadpool.execute(new test_runnable());


    }
    public static class test_runnable implements Runnable{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getId());
            throw new RuntimeException();
        }
    }
}
