package practice.socket;

import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

@Slf4j
public class nio_server implements Runnable{
    private Selector selector = null;
    private ServerSocketChannel ssc = null;
    private Thread thread = new Thread(this);

    private volatile boolean live = true;

    public void start() throws Exception {
        //创建多路复用器
        selector = Selector.open();
        //创建ServerSocket
        ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(9000));
        ssc.configureBlocking(false);
        //多次注册会覆盖
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        thread.start();

    }


    @Override
    public void run() {

    }
}
