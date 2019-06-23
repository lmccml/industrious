package practice.concurrent;

public class thread_one extends Thread {
    @Override
    public void run() {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            System.out.println(this.isInterrupted());
            System.out.println("我被中断了！！！处理后续事情中！！！！");
        }
    }
}
