package concurrent;

/**
 * @author lmc
 * @date 2020/3/17 11:21
 */
public class InterruptTest {
    public static Object object = new Object();
    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                //while (true) {
                    System.out.println("我是线程1");
                //}
                try {
                    Thread.currentThread().join();
                } catch (Exception e) {
                    System.out.println("捕获异常后线程1" + Thread.currentThread().isInterrupted());
                    System.out.println("线程1被中断");
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (Exception e) {
                    System.out.println("捕获异常后线程2" + Thread.currentThread().isInterrupted());
                    System.out.println("线程2被中断");
                } finally {
                }

            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (object) {
                    try {
                        object.wait(10000);
                    } catch (Exception e) {
                        System.out.println("捕获异常后线程3" + Thread.currentThread().isInterrupted());
                        System.out.println("线程3被中断");
                    } finally {

                    }


                }

            }
        });

        t1.start();
        System.out.println(t1.getState());
        t2.start();
        t3.start();
        //interrupt是一个状态操作，正常运行不会捕获异常的方法不会受中断影响，只有那些抛出InterruptedException才会受其影响
        //t1.interrupt();
        t2.interrupt();
        t3.interrupt();
        System.out.println("t1:" + t1.isInterrupted());
        System.out.println("t2:" + t2.isInterrupted());
        System.out.println("t3:" + t3.isInterrupted());
        System.out.println(t1.getState());//TERMINATED ,运行完就TERMINATED了
        t1.interrupt(); //join之后调用interrupt会受其影响
    }
}
