package concurrent;

import java.util.concurrent.*;

public class CallableFuture {
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

        //原生执行方法
        FutureTask<String> futureTask = new FutureTask<>(new CallTask());
        new Thread(futureTask).start();
        String result = futureTask.get();
        System.out.println(result);


    }
}
