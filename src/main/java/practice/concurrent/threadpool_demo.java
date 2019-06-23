package practice.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class threadpool_demo {
    public static void main(String[] args) {
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
