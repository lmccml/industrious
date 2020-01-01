package concurrent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;

/**
 * @author lmc
 * @date 2019/12/31
 */
public class test {
    //自定义AQS锁
    private static Lock lock = new CustomAQSLock();

    public static void main(String[] args) {
        test test = new test();
        ThreadTask threadTask1 = test.new ThreadTask("Task 1");
        ThreadTask threadTask2 = test.new ThreadTask("Task 2");
        ThreadTask threadTask3 = test.new ThreadTask("Task 3");
        threadTask1.start();
        threadTask2.start();
        threadTask3.start();

        //自定义sync重量锁
        final CustomSyncLockImpl customSyncLock = new CustomSyncLockImpl();
        //创建4个线程
        for (int i = 1; i < 5; i++) {

            new Thread("T" + i) {
                @Override
                public void run() {
                    try {
                        customSyncLock.lock(10000);
                        System.out.println(Thread.currentThread().getName() + " have the lock Monitor");
                        work();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (CustomSyncLock.TimeOutException e) {
                        System.out.println(Thread.currentThread().getName() + " time out");
                    } finally {
                        customSyncLock.unlock();
                    }
                };
            }.start();
        }
    }

    private static void work() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " is Working...");
        Thread.sleep(40_000);
    }

    class ThreadTask extends Thread
    {
        private SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");

        public ThreadTask(String name)
        {
            super(name);
        }

        @Override
        public void run()
        {
            lock.lock();
            try
            {
                System.out.println(Thread.currentThread().getName() + ", Time: " + sdf.format(new Date()));
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally
            {
                lock.unlock();
            }
        }
    }

}
