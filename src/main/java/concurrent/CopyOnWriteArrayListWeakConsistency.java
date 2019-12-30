package concurrent;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author lmc
 * @date 2019/12/30
 * CopyOnWriteArrayList的弱一致性
 */
public class CopyOnWriteArrayListWeakConsistency {
    public static void main(String[] args) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        //CopyOnWriteArrayList中法代器的弱一致性，返回迭代器后，其他线程对list的增删改对迭代器是不可见的
        Iterator<String> iterator = list.iterator();

        Thread t0 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(iterator.next() + "t0");//1
                    //保证是在remove方法后
                    Thread.sleep(100);
                    System.out.println(iterator.next() + "t0");//3
                } catch (InterruptedException e) {

                }
            }
        });

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(list.get(1) + "t1");//2
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                list.remove(1);
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(list.get(1) + "t3");//3
            }
        });

        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                    System.out.println(list.get(1) + "t4");//3
                } catch (InterruptedException e) {

                }
            }
        });

        t0.start();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
