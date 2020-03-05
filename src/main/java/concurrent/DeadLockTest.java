package concurrent;

/**
 * @author lmc
 * @date 2020/3/5 10:02
 */
public class DeadLockTest {

    static class testA {

    }

    static class testB {

    }
    public static void main(String[] args) throws Exception {
        testA A = new testA();
        testB B = new testB();
        Thread thredaA = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (A) {
                    try {
                        System.out.println("我获取线程" + Thread.currentThread().getName() + "的锁");
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }
                    synchronized (B) {
                        System.out.println("我获取线程" + Thread.currentThread().getName() + "的锁");

                    }
                }


            }
        });

        Thread thredaB = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (B) {
                    try {
                        System.out.println("我获取线程" + Thread.currentThread().getName() + "的锁");
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }
                    synchronized (A) {
                        System.out.println("我获取线程" + Thread.currentThread().getName() + "的锁");
                    }
                }

            }
        });

        thredaA.start();
        thredaB.start();
        thredaA.join();
        thredaB.join();

        //1.top命令找出java进程11111
        //2.ps -mp pid -o THREAD,tid,time  找到%cpu较大的tid
        //3.printf %x\n 12345 打印十六进制tid数3039
        //4.jstack 11111 |grep 3039 -A 50  （实际文本中显示的是nid=0x3039）
        //5.jmap -dump:format=b,file=/export/logs/xxx.xxx.local/HeapDump.bin;获取这个dump文件后，通过
        //Java内存分析工具MAT将其打开，这个默认打开文件最大为512MB，需要修改MemoryAnalyzer.ini
        //文件中-Xmx6144m,打开dump文件，进入Dominator Tree视图，周到内存最大的对象，单机See stacktrace后，进入线程栈的视图，查看哪个类持续加载这个文件，定位问题
    }
}
