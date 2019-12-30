package concurrent;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.BinaryOperator;
import java.util.function.LongBinaryOperator;

/**
 * @author lmc
 * @date 2019/12/29
 */
public class AtomicTest {
//    ，使用LongAdder 时，则是在内部维护多个Ce ll 变量，每个Cell 里面
//    有一个初始值为0 的long 型变量，这样，在同等并发量的情况下，争夺单个变量更新操
//    作的线程量会减少，这变相地减少了争夺共享资源的并发量。
    LongAdder longAdder = new LongAdder();

    AtomicInteger atomicInteger = new AtomicInteger(0);

    //LongAdder 其实是LongA ccumulator 的一个特例， 调用LongAdder 就相当于使用下面的方式调用LongAccumulator
    LongAccumulator longAccumulator = new LongAccumulator(new LongBinaryOperator() {
        @Override
        public long applyAsLong(long left, long right) {
            return left + right;
        }
    }, 0);

    public static void main(String[] args) throws Exception {
        AtomicTest atomicTest = new AtomicTest();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(atomicTest.atomicInteger.get() < 100) {
                    atomicTest.longAdder.increment();
                    atomicTest.longAccumulator.accumulate(1);
                    int temp = atomicTest.atomicInteger.incrementAndGet();
                    System.out.println("线程1执行了" + temp);
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(atomicTest.atomicInteger.get() < 100) {
                    atomicTest.longAdder.increment();
                    int temp = atomicTest.atomicInteger.incrementAndGet();
                    System.out.println("线程2执行了" + temp);
                }
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(atomicTest.longAdder.intValue());
    }

}
