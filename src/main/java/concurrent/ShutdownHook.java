package concurrent;

import java.util.concurrent.TimeUnit;

/**
 * @author lmc
 * @date 2020/3/23 16:15
 */
public class ShutdownHook {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    System.out.println("The hook thread 2 is running.");
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                System.out.println("The program will exit.");
            }
        });
    }
}
