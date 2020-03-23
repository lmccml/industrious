package concurrent.assitpackage;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @author lmc
 * @date 2020/3/20 9:03
 */
public class ThreadCreate extends Thread {
    @Override
    public void run() {
        super.run();
        System.out.println("我是创建多线程方式1！");
    }

    public static void main(String[] args) {
        ThreadCreate threadCreate = new ThreadCreate();
        threadCreate.start();

        new Thread(new ThreadCreateRunnable()).start();

        FutureTask<String> futureTask = new FutureTask<>(new ThreadCreateCallable());
        new Thread(futureTask).start();//实质上还是以Callable对象来创建并启动线程
        try {
            System.out.println(futureTask.get());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

        Executors.newFixedThreadPool(1).submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("我是创建多线程方式4！");
            }
        });
    }

    static class ThreadCreateRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("我是创建多线程方式2！");
        }
    }

    static class ThreadCreateCallable implements Callable<String> {
        @Override
        public String call() {
            return "我是创建多线程方式3！";
        }
    }


}

