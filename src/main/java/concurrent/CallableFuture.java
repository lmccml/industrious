package concurrent;

import java.util.concurrent.*;

public class CallableFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es =  Executors.newCachedThreadPool();
        /*
        ExcutorService中的excutor和submit方法的区别
        两者都是将一个线程任务添加到线程池中并执行；
        1、excutor没有返回值，submit有返回值，并且返回执行结果Future对象
        2、excutor不能提交Callable任务，只能提交Runnable任务，submit两者任务都可以提交
        3、在submit中提交Runnable任务，会返回执行结果Future对象，但是Future调用get方法将返回null（Runnable没有返回值）
         */
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
