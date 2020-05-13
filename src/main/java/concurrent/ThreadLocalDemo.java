package concurrent;

/**
 * @author lmc
 * @date 2020/3/26 15:06
 */
public class ThreadLocalDemo {
    public static void main(String[] args) throws Exception{
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        threadLocal.set("value1");
        new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal.set("value_thread0");
                Thread thread = Thread.currentThread();
                System.out.println(Thread.currentThread().getName() + threadLocal.get());
            }
        }).start();
        threadLocal.set("value_main");
        System.out.println(Thread.currentThread().getName() + threadLocal.get());
    }


}
