package concurrent;

import java.util.concurrent.locks.LockSupport;

/**
 * @author lmc
 * @date 2020/3/17 10:36
 */
public class ThreadStatusSrc {
    public static Thread.State toThreadState(int var0) {
        if ((var0 & 4) != 0) {
            return Thread.State.RUNNABLE;
        } else if ((var0 & 1024) != 0) {
            return Thread.State.BLOCKED;
        } else if ((var0 & 16) != 0) {
            return Thread.State.WAITING;
        } else if ((var0 & 32) != 0) {
            return Thread.State.TIMED_WAITING;
        } else if ((var0 & 2) != 0) {
            return Thread.State.TERMINATED;
        } else {
            return (var0 & 1) == 0 ? Thread.State.NEW : Thread.State.RUNNABLE;
        }
    }
}
