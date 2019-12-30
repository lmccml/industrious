package concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author lmc
 * @date 2019/12/29
 */
public class AtomicStampedReferenceTest {
    private static AtomicInteger atomicInt = new AtomicInteger(100);
    //AtomicStampedReference类给每个变量的状态值都配备了一个时间戳， 避免了ABA问题的产生。
    private static AtomicStampedReference atomicStampedRef = new AtomicStampedReference(100, 0);
    public static void main(String[] args) throws Exception {
        Thread intT1 = new Thread(new Runnable() {
            @Override
            public void run() {
                atomicInt.compareAndSet(100, 101);
                atomicInt.compareAndSet(101, 100);
            }
        });

        Thread intT2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                }
                boolean c3 = atomicInt.compareAndSet(100, 101);
                System.out.println(c3); // true
            }
        });

        intT1.start();
        intT2.start();
        intT1.join();
        intT2.join();

        Thread refT1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                }
                atomicStampedRef.compareAndSet(100, 101, atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
                System.out.println(atomicStampedRef.getStamp() + "A换成B");
                atomicStampedRef.compareAndSet(101, 100, atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
                System.out.println(atomicStampedRef.getStamp() + "B换成A");
            }
        });

        Thread refT2 = new Thread(new Runnable() {
            int stamp = 0;
            @Override
            public void run() {
                try {
                    //这个前后结果不用
                    stamp = atomicStampedRef.getStamp();
                    TimeUnit.SECONDS.sleep(2);
                    //stamp = atomicStampedRef.getStamp();
                    System.out.println(stamp + "A-》B");
                } catch (InterruptedException e) {
                }
                boolean c3 = atomicStampedRef.compareAndSet(100, 101, stamp, stamp + 1);
                System.out.println(c3); // false
            }
        });

        refT1.start();
        refT2.start();
    }
}
