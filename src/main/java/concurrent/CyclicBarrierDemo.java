package concurrent;

import org.springframework.http.converter.json.GsonBuilderUtils;

import java.util.concurrent.CyclicBarrier;

/*
    CountDownLatch和CyclicBarrier都有让多个线程等待同步然后再开始下一步动作的意思
    CyclicBarrier的计数器由自己控制，
    而CountDownLatch的计数器则由使用者来控制，
    在CyclicBarrier中线程调用await方法不仅会将自己阻塞还会将计数器减1，
    而在CountDownLatch中线程调用await方法只是将自己阻塞而不会减少计数器的值。
    CyclicBarrier 的源码实现和 CountDownLatch 大相径庭，
    CountDownLatch 基于 AQS 的共享模式的使用，
    而CyclicBarrier 基于 Condition 来实现的。
 */
public class CyclicBarrierDemo {
    private static CyclicBarrier cyclicBarrier;
    static class CyclicBarrierThread extends Thread{
        @Override
        public void run() {
            //等待
            try {
                System.out.println(Thread.currentThread().getName() + "到了" );
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName() + "执行完毕");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        cyclicBarrier = new CyclicBarrier(4, new Runnable() {
            @Override
            public void run() {
                //这是大家最后执行的方法
                System.out.println("人到齐了，开会吧....");
            }
        });

        for(int i = 0 ; i < 9 ; i++){
            Thread tt = new CyclicBarrierThread();
            tt.start();
        }
        Thread.sleep(1000);
        System.out.println("最终等待的数量" + cyclicBarrier.getNumberWaiting());
    }
}
