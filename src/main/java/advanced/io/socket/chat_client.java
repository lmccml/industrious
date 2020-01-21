package advanced.io.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;

@Slf4j
public class chat_client implements Runnable {
    private static final Charset charset = Charset.forName("UTF-8");

    private Selector selector;

    private SocketChannel socketChannel;

    private Thread thread = new Thread(this);

    private ByteBuffer buffer = ByteBuffer.allocate(1024);

    private Queue<String> queue = new ConcurrentLinkedDeque<String>();

    private volatile boolean live = true;

    public void start() throws Exception {
        selector = Selector.open();
        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 9000));
        if(socketChannel.finishConnect()) {
            socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            thread.start();
        }
    }


    @Override
    public void run() {
        try {
            while (live && !Thread.interrupted()) {
                if (selector.select(1000) == 0) {
                    continue;
                }
                Set<SelectionKey> set = selector.selectedKeys();
                Iterator<SelectionKey> it = set.iterator();
                if (it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();
                    SocketChannel sc = null;
                    int r = 0;
                    String s = null;

                    if (key.isValid() && key.isReadable()) {
                        sc = (SocketChannel) key.channel();
                        StringBuilder sb = new StringBuilder();
                        buffer.clear();
                        while ((r = sc.read(buffer)) > 0) {
                            buffer.flip();
                            sb.append(charset.decode(buffer));
                            buffer.clear();
                            s = sb.toString();
                            if ("\n".equals(s)) {
                                break;
                            }
                            String[] sa = s.split("\n");
                            for (String a : sa) {
                                if (null != a && "".equals(a)) {
                                    System.out.println(a);
                                }
                            }
                        }
                    }

                    if (key.isValid() && key.isWritable() && !queue.isEmpty()) {
                        s = queue.poll();
                        sc = (SocketChannel) key.channel();
                        ByteBuffer buf = ByteBuffer.wrap(s.getBytes("UTF-8"));
                        buf.limit(s.length());
                        while (buf.hasRemaining() && (r = socketChannel.write(buf)) > 0) {
                            log.info("write data to server");
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stream_util.close(selector);
            stream_util.close(socketChannel);
        }
    }

    public void close() {
        live = false;
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stream_util.close(selector);
            stream_util.close(socketChannel);
        }

    }

    public boolean isAlive() {
        return thread.isAlive();
    }

    public void send(String s) {
        queue.add(s);
    }

    public static void main(String[] args) {
        BufferedReader ir = null;
        chat_client client = new chat_client();
        try {
            client.start();
            String cmd = null;
            ir = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("say bye to exit!");
            while ((cmd = ir.readLine()) != null && client.isAlive()) {
                if (cmd != null && !"".equals(cmd)) {
                    client.send(cmd);
                }
                if ("bye".equalsIgnoreCase(cmd)) {
                    client.close();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stream_util.close(ir);
        }

    }
}
