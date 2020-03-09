package concurrent;

/**
 * @author lmc
 * @date 2020/3/6 10:21
 */
public class DeadLockByNotify {
    /* OutTurn类中的sub和main方法都是同步方法，所以多个调用sub和main方法的线程都会处于阻塞状态，等待一个正在运行的线程来唤醒它们。下面分别分析一下使用notify和notifyAll方法唤醒线程的不同之处：
   上面的代码使用了notify方法进行唤醒，而notify方法只能唤醒一个线程，其它等待的线程仍然处于wait状态，假设调用sub方法的线程执行完后（即System. out .println("sub  ---- " + count )执行完之后），
   所有的线程都处于等待状态，此时在sub方法中的线程执行了isSub=false语句后又执行了notify方法，这时如果唤醒的是一个sub方法的调度线程，那么while循环等于true，
   则此唤醒的线程也会处于等待状态，此时所有的线程都处于等待状态，那么也就没有了运行的线程来唤醒它们，这就发生了死锁。
   如果使用notifyAll方法来唤醒所有正在等待该锁的线程，那么所有的线程都会处于运行前的准备状态（就是sub方法执行完后，唤醒了所有等待该锁的状态,注：不是wait状态），
   那么此时，即使再次唤醒一个sub方法调度线程，while循环等于true，唤醒的线程再次处于等待状态，那么还会有其它的线程可以获得锁，进入运行状态。
   总结：notify方法很容易引起死锁，除非你根据自己的程序设计，确定不会发生死锁，notifyAll方法则是线程的安全唤醒方法。
   附：
   notify和notifyAll的区别：
   notify()和notifyAll()都是Object对象用于通知处在等待该对象的线程的方法。
   void notify(): 唤醒一个正在等待该对象的线程。
   void notifyAll(): 唤醒所有正在等待该对象的线程。
   两者的最大区别在于：
   notifyAll使所有原来在该对象上等待被notify的线程统统退出wait的状态，变成等待该对象上的锁，一旦该对象被解锁，他们就会去竞争。
   notify他只是选择一个wait状态线程进行通知，并使它获得该对象上的锁，但不惊动其他同样在等待被该对象notify的线程们，
   当第一个线程运行完毕以后释放对象上的锁，此时如果该对象没有再次使用notify语句，即便该对象已经空闲，其他wait状态等待的线程由于没有得到该对象的通知，
   继续处在wait状态，直到这个对象发出一个notify或notifyAll，它们等待的是被notify或notifyAll，而不是锁。
   */
    private boolean isSub = true;
    private int count = 0;

    public synchronized void sub() {
        try {
            while (!isSub) {
                this.wait();
            }
            System.out.println("sub ---- " + count);
            isSub = false ;
            this.notify();   // dead with notify , notifyAll ok
        } catch (Exception e) {
            e.printStackTrace();
        }
        count++;

    }

    public synchronized void main() {
        try {
            while (isSub) {
                this.wait();
            }
            System.out.println("main (((((((((((( " + count);
            isSub = true ;
            this.notify();  // dead with notify , notifyAll ok
        } catch (Exception e) {
            e.printStackTrace();
        }
        count++;
    }

    public static void main(String[] args) {
        // System.out.println("lock");

        final DeadLockByNotify ot = new DeadLockByNotify();

        for (int j = 0; j < 10; j++) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 5; i++) {
                        ot.sub();
                    }
                }
            }, "mysub").start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 5; i++) {
                        ot.main();
                    }
                }
            }, "mymain").start();
        }
    }
}
