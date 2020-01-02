package concurrent;

import java.util.concurrent.Semaphore;

/**
 * @author lmc
 * @date 2020/1/1
 */
public class SemaphoreTest {
    static volatile Semaphore semaphore = new Semaphore(0);

    public static void main(String[] args) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("1");
                semaphore.release();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("2");
                semaphore.release();
            }
        }).start();

        semaphore.acquire(3);

        System.out.println("1和2都执行了！");

    }
}
