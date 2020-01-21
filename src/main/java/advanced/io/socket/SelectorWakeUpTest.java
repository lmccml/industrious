package advanced.io.socket;

import javax.print.attribute.standard.RequestingUserName;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * @author lmc
 * @date 2020/1/8 16:46
 */
public class SelectorWakeUpTest {
    public static void main(String[] args) throws Exception{
        Selector selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress("localhost", 8888));
        boolean isRun = true;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                    selector.wakeup();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        while (isRun) {
            selector.select();
            System.out.println("select阻塞被wakeUp了！");
            ssc.close();
        }

    }
}
