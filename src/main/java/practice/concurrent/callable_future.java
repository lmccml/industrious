package practice.concurrent;

import sun.jvm.hotspot.tools.ObjectHistogram;

import java.util.concurrent.*;

public class callable_future {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es =  Executors.newCachedThreadPool();
        Future<String> ft = es.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(1234);
                return "ok";
            }
        });

        String aa = ft.get();
        System.out.println(aa);

        es.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1234);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("同步线程执行完了");
            }
        });

    }
}
