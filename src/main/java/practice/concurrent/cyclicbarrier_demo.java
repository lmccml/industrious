package practice.concurrent;

import java.util.concurrent.CyclicBarrier;

/*
    CountDownLatch和CyclicBarrier都有让多个线程等待同步然后再开始下一步动作的意思
    但是CountDownLatch的下一步的动作实施者是主线程，具有不可重复性；
    而CyclicBarrier的下一步动作实施者还是“其他线程”本身，具有往复多次实施动作的特点。
 */
public class cyclicbarrier_demo {
    private static CyclicBarrier cyclicBarrier;

    static class CyclicBarrierThread extends Thread{
        public void run() {
            System.out.println(Thread.currentThread().getName() + "到了");
            //等待
            try {
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        cyclicBarrier = new CyclicBarrier(5, new Runnable() {
            @Override
            public void run() {
                //这是大家最后执行的方法
                System.out.println("人到齐了，开会吧....");
            }
        });

        for(int i = 0 ; i < 5 ; i++){
            Thread tt = new CyclicBarrierThread();
            tt.start();
            tt.interrupt();//它可以迅速中断被阻塞的线程，抛出一个InterruptedException，把线程从阻塞状态中解救出来
        }
    }

}
