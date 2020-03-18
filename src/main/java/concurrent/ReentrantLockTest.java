package concurrent;

import com.sun.corba.se.impl.orbutil.concurrent.Mutex;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lmc
 * @date 2020/3/18 9:39
 */
public class ReentrantLockTest {
    //不可以重入，
    Mutex mutex = new Mutex();
    Lock lock = new ReentrantLock();
    public static void main(String[] args) {
        ReentrantLockTest reentrantLockTest = new ReentrantLockTest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLockTest.out(1);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLockTest.out(2);
            }
        }).start();


    }
    void out(int i) {
        try {
            mutex.acquire();
            //再次执行会阻塞在这里
            //mutex.acquire();
            lock.lock();//计数+，成对出现
            lock.lock();
            System.out.println("我是线程" + i);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mutex.release();
            lock.unlock();//计数-，成对出现
            lock.unlock();
        }
    }
}
