package socket;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

@Slf4j
public class nio_client {
    public static void main(String[] args) {
        SocketChannel sc = null;
        FileChannel fc = null;
        try {
            sc = SocketChannel.open();
            sc.configureBlocking(true);
            sc.connect(new InetSocketAddress("127.0.0.1", 9000));
            if(!sc.finishConnect()){
                return;
            }
            //从文件中读取内容，并通过socket发送
            fc = new FileInputStream(new File("/users/lmc/Documents/test.txt")).getChannel();
            ByteBuffer buf = ByteBuffer.allocate(10240);
            int r = 0;
            while ((r = fc.read(buf)) > 0) {
                buf.flip();
                while(buf.hasRemaining() && (r = sc.write(buf)) > 0 ) {
                    log.info("send msg to server!", r );
                }
                buf.clear();
            }
            //读取服务器返回
            while((r = sc.read(buf)) > 0 ) {
                log.info("get msg from server");
            }
            buf.flip();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stream_util.close(fc);
            stream_util.close(sc);
        }
    }
}
