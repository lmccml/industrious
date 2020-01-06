package io.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

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
        try {
            while(live && !Thread.interrupted()){
                if(selector.select(1000) ==0){
                    continue;
                }
                Set<SelectionKey> set = selector.selectedKeys();
                Iterator<SelectionKey> it = set.iterator();
                if(it.hasNext()){
                    SelectionKey key = it.next();
                    it.remove();
                    if(key.isValid() && key.isAcceptable()) {
                        this.onAcceptable(key);
                    }
                    if(key.isValid() && key.isReadable()) {
                        this.onReadable(key);
                    }
                    if(key.isValid() && key.isWritable()) {
                        this.onWriteble(key);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void onAcceptable(SelectionKey key) throws Exception {
        ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
        SocketChannel sc = null;
        try {
            sc = ssc.accept();
            if(null != sc) {
                //服务端使用非阻塞模式
                sc.configureBlocking(false);
                //在新建立的socket通道上注册可读事件。因为在没有读完数据前，不会向客户端返回应答，此外，为每个新建立的连接都会创建一个缓冲区，并作为附件到SelectionKey方便调用
                sc.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(10240));
            }

        } catch (IOException e) {
            //一旦出现异常，关闭当前连接
            stream_util.close(sc);
            e.printStackTrace();
        } finally {
        }
    }

    public void onReadable(SelectionKey key) throws Exception {
        SocketChannel sc = (SocketChannel) key.channel();
        ByteBuffer buf = (ByteBuffer) key.attachment();
        FileChannel fc = null;
        try {
            InetSocketAddress isa = (InetSocketAddress) sc.getRemoteAddress();
            fc = new FileOutputStream(new File(String.format("/users/lmc/Documents/test_result.txt", isa.getPort()))).getChannel();
            int r = 0;
            buf.clear();
            while ((r = sc.read(buf)) > 0) {
                buf.flip();
                r = fc.write(buf);
                buf.clear();
            }

            sc.register(key.selector(), SelectionKey.OP_WRITE, "OK");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stream_util.close(sc);
            stream_util.close(fc);
        }
    }

    public void onWriteble(SelectionKey key) throws Exception {
        SocketChannel sc = (SocketChannel)key.channel();
        String s = (String) key.attachment();
        try {
            byte[] ba = s.getBytes("UTF-8");
            ByteBuffer buf = ByteBuffer.wrap(ba);
            //用上面的方法得到的缓冲区，其limit为0，需移动到最后
            buf.limit(ba.length);
            int r = 0;
            //将缓冲区的内容全部发送出去
            while(buf.hasRemaining() && (r = sc.write(buf)) > 0) {
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            stream_util.close(sc);
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
        nio_server nio_server = new nio_server();
        BufferedReader br = null;
        try {
            nio_server.start();
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
            nio_server.close();
        }
    }

}
