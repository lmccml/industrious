package concurrent;

import java.util.concurrent.ThreadPoolExecutor;

public class InteruptedBlocked {
    public static void main(String[] args) {
        Thread thread_one = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("i'm one!");
            }
        });


        Thread thread_two = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(12234L);
                    System.out.println(Thread.currentThread().isInterrupted());
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
                System.out.println("i'm two!");
            }
        });

        thread_one.start();
        thread_two.start();
        System.out.println(thread_two.isInterrupted());
        thread_two.interrupt();
        System.out.println(thread_two.isInterrupted());

    }

}


