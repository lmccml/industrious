package concurrent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lmc
 * @date 2020/3/9 9:45
 */
public class FalseAwaken {
    private List<String> list = new ArrayList<String>();

    public synchronized void push(String value) {
        synchronized (this) {
            list.add(value);
            notify();
        }
    }

    public synchronized String pop() throws InterruptedException {
        synchronized (this) {
            if (list.size() <= 0) {
                wait();
            }
            return list.remove(list.size() - 1);
        }
    }
    /*
    问题：  这段代码大多数情况下运行正常，但是某些情况下会出问题。什么时候会出现什么问题？如何修正？
代码分析：
从整体上，在并发状态下，push和pop都使用了synchronized的锁，来实现同步，同步的数据对象是基于List的数据；大部分情况下是可以正常工作的。
问题描述：
状况1：
1.  假设有三个线程： A,B,C.  A 负责放入数据到list,就是调用push操作， B,C分别执行Pop操作，移除数据。
2.  首先B先执行，于pop中的wait()方法处，进入waiting状态，进入等待队列，释放锁。
3.  A首先执行放入数据push操作到List，在调用notify()之前； 同时C执行pop()，由于synchronized，被阻塞，进入Blocked状态，放入基于锁的等待队列。注意，这里的队列和2中的waiting等待队列是两个不同的队列。
4.    A线程调用notify()，唤醒等待中的线程B。
5.    如果此时， C获取到基于对象的锁，则优先执行，执行pop方法，获取数据，从list移除一个元素。
6.  然后，B获取到竞争锁，B中调用list.remove(list.size() - 1)，则会报数据越界exception。
状况2：
1.  相同于状况1
2.  B、C都处于等待waiting状态，释放锁。等待notify()、notifyAll()操作的唤醒。
3.  存在被虚假唤醒的可能。
何为虚假唤醒？
虚假唤醒就是一些obj.wait()会在除了obj.notify()和obj.notifyAll()的其他情况被唤醒，而此时是不应该唤醒的。
解决的办法是基于while来反复判断进入正常操作的临界条件是否满足：
    synchronized (obj) {
            while (<condition does not hold>)
                obj.wait();
            ... // Perform action appropriate to condition
        }
如何修复问题？
#1.  使用可同步的数据结构来存放数据，比如LinkedBlockingQueue之类。由这些同步的数据结构来完成繁琐的同步操作。
#2.  双层的synchronized使用没有意义，保留外层即可。
#3.  将if替换为while，解决虚假唤醒的问题。
     */
}
