package concurrent;

import java.util.Collection;

/**
 * @author lmc
 * @date 2019/12/31
 */
public interface CustomSyncLock {
    class TimeOutException extends Exception {
        public TimeOutException(String message) {
            super(message);
        }
    }

    void lock() throws InterruptedException;

    void lock(long mills) throws InterruptedException, TimeOutException;

    void unlock();

    Collection<Thread> getBlockedThread();

    int getBlockedSize();
}
