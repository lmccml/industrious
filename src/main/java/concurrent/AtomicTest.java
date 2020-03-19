package concurrent;

import java.util.concurrent.atomic.*;
import java.util.function.BinaryOperator;
import java.util.function.LongBinaryOperator;

/**
 * @author lmc
 * @date 2019/12/29
 */
public class AtomicTest {
    //3个基本类型
    AtomicLong atomicLong = new AtomicLong();
    AtomicInteger atomicInteger = new AtomicInteger(0);
    AtomicBoolean atomicBoolean = new AtomicBoolean(true);

    //3个原子更新数组
    AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(2);
    AtomicLongArray atomicLongArray = new AtomicLongArray(5);
    AtomicReferenceArray atomicReferenceArray = new AtomicReferenceArray(33);

    //3个原子更新引用类型
    //AtomicReference：原子更新引用类型
    //AtomicReferenceFieldUpdater：原子更新引用类型里的字段。
    //AtomicMarkableReference：原子更新带有标记位的引用类型。可以原子更新一个布尔类型的标记位和引用类型。构造方法是AtomicMarkableReference（V initialRef，boolean initialMark）。
    AtomicReference atomicReference = new AtomicReference();
    AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(String.class, String.class, "name");


    //3个原子更新字段类
    //AtomicIntegerFieldUpdater：原子更新整型的字段的更新器。
    //AtomicLongFieldUpdater：原子更新长整型字段的更新器。
    //AtomicStampedReference：原子更新带有版本号的引用类型。该类将整数值与引用关联起来，可用于原子的更新数据和数据的版本号，可以解决使用CAS进行原子更新时可能出现的ABA问题。
    /*要想原子地更新字段类需要两步。
    第一步，因为原子更新字段类都是抽象类，每次使用的时候必须使用静态方法newUpdater()创建一个更新器，并且需要设置想要更新的类和属性。
    第二步，更新类的字段（属性）必须使用public volatile修饰符。
     */

    //LongAdder 其实是LongAccumulator的一个特例，调用LongAdder 就相当于使用下面的方式调用LongAccumulator
     /*使用LongAdder 时，则是在内部维护多个Cell 变量，每个Cell里面
    有一个初始值为0 的long 型变量，这样，在同等并发量的情况下，争夺单个变量更新操
    作的线程量会减少，这变相地减少了争夺共享资源的并发量。
    */
    LongAdder longAdder = new LongAdder();


    LongAccumulator longAccumulator = new LongAccumulator(new LongBinaryOperator() {
        @Override
        public long applyAsLong(long left, long right) {
            return left + right;
        }
    }, 0);

    public static void main(String[] args) throws Exception {
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(String.class, String.class, "name");
        atomicReferenceFieldUpdater.compareAndSet("dog1", "dog2", "test") ;

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
