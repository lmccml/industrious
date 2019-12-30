package concurrent;

import java.util.concurrent.Callable;

/**
 * @author lmc
 * @date 2019/12/24
 */
public class call_task implements Callable<String> {
    @Override
    public String call() throws Exception {
        synchronized (this){
            this.wait();
        }
        System.out.println("to do something!");
        return "ok";
    }
}
