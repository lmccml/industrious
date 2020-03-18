package concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * @author lmc
 * @date 2020/3/18 17:38
 * 队列中的元素必须实现Delayed接口，在创建元素时可以指定多久才能从队列中获取当前元素。只有在延迟期满时才能从队列中提取元素。
 */
public class DelayedTask implements Runnable, Delayed {
    /*
    这是一个无界阻塞队列 （ BlockingQueue ），用于放置实现了 Delayed 接口的对象，其中的对象只能在其到期时才能从队列中取走。
    这种队列是有序的，因此队首对象的延迟到期的时间最长。如果没有任何延迟到期，
    那么就不会有队首元素，并且 poll() 将返回 null（正因为这样，你不能将 null 放置到这种队列中）。
    下面是一个示例，其中的 Delayed 对象自身就是任务，而 DelayedTaskConsumer 将最“紧急”的任务（到期时间最长的任务）从队列中取出
    然后运行它。注意的是这样 DelayQueue 就成为了优先级队列的一种变体。
     */
    private static int counter = 0;
    private final int id = counter++;
    private final int delta;
    private final long trigger;
    protected static List<DelayedTask> sequence =
            new ArrayList<>();
    DelayedTask(int delayInMilliseconds) {
        delta = delayInMilliseconds;
        trigger = System.nanoTime() +
                NANOSECONDS.convert(delta, MILLISECONDS);
        sequence.add(this);
    }
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(
                trigger - System.nanoTime(), NANOSECONDS);
    }
    @Override
    public int compareTo(Delayed arg) {
        DelayedTask that = (DelayedTask)arg;
        if(trigger < that.trigger) {
            return -1;
        }
        if(trigger > that.trigger) {
            return 1;
        }
        return 0;
    }
    @Override
    public void run() {
        System.out.print(this + " ");
    }
    @Override
    public String toString() {
        return
                String.format("[%d] Task %d", delta, id);
    }
    public String summary() {
        return String.format("(%d:%d)", id, delta);
    }
    public static class EndTask extends DelayedTask {
        EndTask(int delay) { super(delay); }
        @Override
        public void run() {
            sequence.forEach(dt ->
                    System.out.println(dt.summary()));
        }
    }

    public static void main(String[] args) throws Exception {
        DelayQueue<DelayedTask> tasks =
                Stream.concat( // Random delays:
                        new Random(47).ints(20, 0, 4000)
                                .mapToObj(DelayedTask::new),
                        // Add the summarizing task:
                        Stream.of(new DelayedTask.EndTask(4000)))
                        .collect(Collectors
                                .toCollection(DelayQueue::new));
        while(tasks.size() > 0) {
            tasks.take().run();
        }
    }
}

