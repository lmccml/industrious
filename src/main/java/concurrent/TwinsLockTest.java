package concurrent;

import org.junit.Test;

import java.util.concurrent.locks.Lock;

/**
 * @author lmc
 * @date 2020/3/18 9:14
 */
public class TwinsLockTest {
    @Test
    public void test() throws Exception{
        final Lock lock = new TwinsLock();
        class Worker extends Thread {
            @Override
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName());
                        Thread.sleep(1000);
                    } catch (Exception e){

                    } finally {
                        lock.unlock();
                    }
                }
            }
        }

// 启动10个线程
        for (int i = 0; i < 10; i++) {
            Worker w = new Worker();
            w.setDaemon(true);
            w.start();
        }

// 每隔1秒换行
        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
            System.out.println();
        }
    }
}
