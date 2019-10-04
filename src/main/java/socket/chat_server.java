package socket;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;

@Slf4j
public class chat_server implements Runnable {

    private Charset charset = Charset.forName("UTF-8");

    private Selector selector = null;

    private ServerSocketChannel ssc = null;

    private Thread thread = new Thread(this);

    private Queue<String> queue = new ConcurrentLinkedDeque<>();

    private boolean live = true;

    public void start() throws Exception {
        selector = Selector.open();
        ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(9000));
        ssc.configureBlocking(false);
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        thread.start();
    }

    @Override
    public void run() {
        try {
            while (live && !Thread.interrupted()) {
                if(selector.select(1000) ==0) {
                    continue;
                }
                ByteBuffer outBuf = null;
                String outMsg = queue.poll();
                if(null != outMsg && !"".equals(outMsg)) {
                    outBuf = ByteBuffer.wrap(outMsg.getBytes("UTF-8"));
                    outBuf.limit(outMsg.length());
                }
                Set<SelectionKey> set = selector.selectedKeys();
                Iterator<SelectionKey> it = set.iterator();
                if(it.hasNext()) {
                    SelectionKey key = it.next();
                    it.remove();
                    if(key.isValid() && key.isReadable()) {
                        this.onReadable(key);
                    }
                    if(key.isValid() && key.isWritable() && outBuf != null) {
                        SocketChannel sc = (SocketChannel)key.channel();
                        this.write(sc, outBuf);
                    }
                    if(key.isValid() && key.isAcceptable()) {
                        this.onAcceptable(key);
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    public void onAcceptable(SelectionKey key) {
        ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
        SocketChannel sc = null;
        try {
            sc = ssc.accept();
            if (sc != null) {
                sc.configureBlocking(false);
                //创建一个读取用的缓冲区，避免重复创建
                sc.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, ByteBuffer.allocate(1024));
            }
        } catch (IOException e) {
            stream_util.close(sc);
            e.printStackTrace();
        } finally {
        }
    }

    public void onReadable(SelectionKey key) {
        SocketChannel sc = (SocketChannel)key.channel();
        ByteBuffer buf = (ByteBuffer)key.attachment();
        int r = 0;
        StringBuilder sb = new StringBuilder();
        String rs = null;
        String remote = null;
        buf.clear();
        try {
            remote =sc.getRemoteAddress().toString();
            while ((r = sc.read(buf)) > 0) {
                buf.flip();
                sb.append(charset.decode(buf));
                buf.clear();
                rs = sb.toString();
                if (rs.endsWith("\n")) {
                    break;
                }
            }
        } catch (IOException e) {
            stream_util.close(sc);
            e.printStackTrace();
        } finally {
        }
        if(null != rs && !"".equals(rs)) {
            String[] sa = rs.split("\n");
            for (String s : sa) {
                if(null != s && !"".equals(s)) {
                    queue.add(String.format("%s: %s\n", remote, s));
                    if("bye".equalsIgnoreCase(s)) {
                        stream_util.close(sc);
                    }
                }
            }

        }

    }

    public void write(SocketChannel sc, ByteBuffer buf) {
        buf.position(0);
        int r = 0;
        try {
            while (buf.hasRemaining() && (r = sc.write(buf)) > 0) {
                log.info("write data to client");
            }
        } catch (IOException e) {
            stream_util.close(sc);
            e.printStackTrace();

        } finally {
        }

    }


    public void close() {
        live = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            stream_util.close(selector);
            stream_util.close(ssc);
        }
    }

    public static void main(String[] args) {
        chat_server chat_server = new chat_server();
        BufferedReader br = null;
        try {
            chat_server.start();
            br = new BufferedReader(new InputStreamReader(System.in));
            String cmd = null;
            while((cmd = br.readLine()) != null){
                if("exit".equalsIgnoreCase(cmd)){
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            stream_util.close(br);
            chat_server.close();
        }
    }

}
