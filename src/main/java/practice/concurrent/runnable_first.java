package practice.concurrent;

import java.util.concurrent.CountDownLatch;

public class runnable_first implements Runnable {
    private CountDownLatch countDownLatch;

    public runnable_first(CountDownLatch ct){
        this.countDownLatch = ct;
    }


    @Override
    public void run() {
        try{
            System.out.println("这个第一个任务可能会很久");
            Thread.sleep(2000L);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null != countDownLatch){
                countDownLatch.countDown();
            }

        }
    }
}
