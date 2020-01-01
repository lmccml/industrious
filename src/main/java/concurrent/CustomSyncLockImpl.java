package concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author lmc
 * @date 2019/12/31
 */
public class CustomSyncLockImpl implements CustomSyncLock {

    // 表示对象的锁是否被占用，true表示对象锁已经被占用
    private boolean initValue;

    // 表示被阻塞线程的集合
    private Collection<Thread> blockedThreadCollection = new ArrayList<>();

    // 记录获得当前对象锁的线程
    private Thread currentThread;

    public CustomSyncLockImpl() {
        this.initValue = false;
    }

    /**
     * 加锁，使用synchronized实现同步
     */
    @Override
    public synchronized void lock() throws InterruptedException {
        // 如果锁已经被占用，则阻塞当前线程
        while (initValue) {
            //将线程加入到阻塞线程集合
            blockedThreadCollection.add(Thread.currentThread());
            this.wait();
        }

        // 锁没有被占用，则当前线程获得锁
        blockedThreadCollection.remove(Thread.currentThread());
        this.initValue = true;
        this.currentThread = Thread.currentThread();
    }

    /**
     * 当线程等待一定时间后，没有释放锁，则其他线程抛出超时异常
     */
    @Override
    public synchronized void lock(long mills) throws InterruptedException, TimeOutException {
        if (mills <= 0)
            lock();

        long hasRemaining = mills;
        long endTime = System.currentTimeMillis() + mills;
        while (initValue) {
            if (hasRemaining <= 0)
                throw new TimeOutException("Time out");
            blockedThreadCollection.add(Thread.currentThread());
            this.wait(mills);
            hasRemaining = endTime - System.currentTimeMillis();
        }

        this.initValue = true;
        this.currentThread = Thread.currentThread();
    }

    /**
     * 释放锁
     */
    @Override
    public synchronized void unlock() {
        // 只有占用锁对象的线程，才能释放锁。不然main线程释放锁，程序会有问题。
        if (Thread.currentThread() == currentThread) {
            this.initValue = false;
            System.out.println(Thread.currentThread().getName() + " release the lock monitor.");
            this.notifyAll();
        }
    }

    @Override
    public Collection<Thread> getBlockedThread() {
        //保证更新集合操作安全，不存在安全问题
        return Collections.unmodifiableCollection(blockedThreadCollection);
    }

    @Override
    public int getBlockedSize() {
        return blockedThreadCollection.size();
    }
}
