package concurrent;

/**
 * @author lmc
 * @date 2020/3/2 14:27
 */
public class CacheVisibility {
    public static volatile boolean flag = false;
    //public static boolean flag = false;
    static class tt extends Thread {
        @Override
        public void run() {
            while (!flag) {
                //do something
            }
            System.out.println("exit run!");
        }
    }

    public static void main(String[] args) throws Exception {
        Thread thread = new tt();
        thread.start();
        Thread.sleep(1000);
        //先sleep的话，切换线程到tt，那么主线程更改的值和线程读取的值不同
        flag = true;
        //Thread.sleep(1000);
        thread.join();
        System.out.println("exit main!");
    }
}
