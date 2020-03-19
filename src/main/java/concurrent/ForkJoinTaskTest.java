package concurrent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author lmc
 * @date 2020/3/19 10:48
 */
public class ForkJoinTaskTest extends RecursiveTask<Long> {
    private static final long MAX = 1000000000L;
    private static final long THRESHOLD = 1000L;
    private long start;
    private long end;

    /*
    ForkJoinPool
    ForkJoinPool是ForkJoin框架中的任务调度器，和ThreadPoolExecutor一样实现了自己的线程池，提供了三种调度子任务的方法：
    execute：异步执行指定任务，无返回结果；
    invoke、invokeAll：异步执行指定任务，等待完成才返回结果；
    submit：异步执行指定任务，并立即返回一个Future对象；

    ForkJoinTask
    Fork/Join框架中的实际的执行任务类，有以下两种实现，一般继承这两种实现类即可。
    RecursiveAction：用于无结果返回的子任务；
    RecursiveTask：用于有结果返回的子任务；
    */

    //在使用Fork/Join时也得注意，不要盲目使用。
    //如果任务拆解的很深，系统内的线程数量堆积，导致系统性能性能严重下降；
    //如果函数的调用栈很深，会导致栈内存溢出；
    public static void main(String[] args) {
        test();
        System.out.println("--------------------");
        testForkJoin();
    }

    public ForkJoinTaskTest(long start, long end) {
        this.start = start;
        this.end = end;
    }

    private static void test() {
        System.out.println("test");
        long start = System.currentTimeMillis();
        Long sum = 0L;
        for (long i = 0L; i <= MAX; i++) {
            sum += i;
        }
        System.out.println(sum);
        System.out.println(System.currentTimeMillis() - start + "ms");
    }

    private static void testForkJoin() {
        System.out.println("testForkJoin");
        long start = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Long sum = forkJoinPool.invoke(new ForkJoinTaskTest(1, MAX));
        System.out.println(sum);
        System.out.println(System.currentTimeMillis() - start + "ms");
    }

    @Override
    protected Long compute() {
        long sum = 0;
        if (end - start <= THRESHOLD) {
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            long mid = (start + end) / 2;

            ForkJoinTaskTest task1 = new ForkJoinTaskTest(start, mid);
            task1.fork();

            ForkJoinTaskTest task2 = new ForkJoinTaskTest(mid + 1, end);
            task2.fork();

            /*forkJoinTask在执行的时候可能会抛出异常，但是我们没办法在主
            线程里直接捕获异常，所以ForkJoinTask提供了isCompletedAbnormally()
            方法来检查任务是否已经抛出异常或已经被取消了，并且可以通过
            ForkJoinTask的getException方法获取异常。
             */
            if(task1.isCompletedAbnormally())
            {
                System.out.println(task1.getException());
            }

            if(task2.isCompletedAbnormally())
            {
                System.out.println(task2.getException());
            }

            return task1.join() + task2.join();
        }
    }
}
