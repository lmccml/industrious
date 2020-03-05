package advanced.task;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author lmc
 * @date 2020/3/5 14:47
 */
public class TimerTest {
    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("定时任务执行！");

            }
        };
        Date date = new Date();
        date.setTime(date.getTime() + 10000);
        date.setTime( System.currentTimeMillis() + 10000);
        //timer.schedule(timerTask, date);
        //time in milliseconds between successive task executions
        //连续任务执行之间的时间(毫秒)
        timer.scheduleAtFixedRate(timerTask, 1000 , 1000); //延迟1秒，每秒执行一次
    }
}
