package concurrent;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author lmc
 * @date 2020/3/17 9:54
 */
public class MultiThread {
    public static void main(String[] args) {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        //不需要获取同步的monitor和synchronizer信息，仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        /*
        1-main                          主线程，执行我们指定的启动类的main方法
        2-Reference Handler             处理引用的线程　
        3-Finalizer                     调用对象的finalize方法的线程，就是垃圾回收的线程　
        4-Signal Dispatcher             分发处理发送给JVM信号的线程　　
        5-Attach Listener               负责接收外部的命令的线程
        6-Monitor Ctrl-Break            监视器控制中断
         */
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("[" + threadInfo.getThreadId() + "] " + threadInfo.
                    getThreadName());
        }
    }
}

