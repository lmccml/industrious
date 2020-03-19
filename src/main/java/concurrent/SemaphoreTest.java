package concurrent;

import java.util.concurrent.Semaphore;

/**
 * @author lmc
 * @date 2020/1/1
 */
public class SemaphoreTest {
    /*
    注意理解是acquire（阻塞）的同一时段只能有2个线程可以获得许可去运行，等release又放了2个许可可以去运行下一组（这里定义的是2个）
     */
    static Semaphore semaphore = new Semaphore(2);

    public static void main(String[] args) throws Exception {
        SemaphoreTest semaphoreTest = new SemaphoreTest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                semaphoreTest.out(1);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                semaphoreTest.out(2);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                semaphoreTest.out(3);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                semaphoreTest.out(4);
            }
        }).start();


    }

    void out(int i) {
        try {
            semaphore.acquire();
            System.out.println(i);
            Thread.sleep(10000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }finally {
            semaphore.release();
        }
    }
}



