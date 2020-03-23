package concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author lmc
 * @date 2020/3/23 11:08
 */
public class WorkerThread {
    public static void main(String[] args) {
        Helper helper = new Helper();
        helper.init();

        // 此处，helper的客户端线程为main线程
        helper.submit("Something...");
        helper.submit("Something1...");
        helper.submit("Something2...");
        helper.submit("Something3...");

    }

    static class Helper {
        private final BlockingQueue<String> workQueue = new ArrayBlockingQueue<String>(
                100);

        // 用于处理队列workQueue中的任务的工作者线程
        private final Thread workerThread = new Thread() {

            @Override
            public void run() {
                String task = null;
                while (true) {
                    try {
                        task = workQueue.take();
                    } catch (InterruptedException e) {
                        break;
                    }
                    System.out.println(doProcess(task));

                }
            }

        };

        public void init() {
            workerThread.start();
        }

        protected String doProcess(String task) {
            return task + "->processed.";
        }

        public void submit(String task) {
            try {
                workQueue.put(task);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                ;
            }
        }

    }

}

