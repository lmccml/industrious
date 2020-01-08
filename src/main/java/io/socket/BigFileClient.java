package io.socket;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lmc
 * @date 2020/1/8 10:28
 */
public class BigFileClient {
    public static void main(String[] args) throws Exception {
        Selector selector = Selector.open();
        SocketChannel sc = SocketChannel.open();
        sc.configureBlocking(false);
        sc.connect(new InetSocketAddress("localhost", 8888));
        sc.register(selector, SelectionKey.OP_CONNECT);
        while (true) {
            if (selector.select(1000) == 0) {
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            if(iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                if(selectionKey.isValid() && selectionKey.isConnectable()) {
                    while (!sc.finishConnect()){
                    }
                    sc.register(selector, SelectionKey.OP_READ);
                }
                if(selectionKey.isValid() && selectionKey.isReadable()) {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(50000);
                    int readLength = sc.read(byteBuffer);
                    byteBuffer.flip();
                    long count = 0;
                    while (readLength != -1) {
                        count = count + readLength;
                        readLength = sc.read(byteBuffer);
                        System.out.println(count);
                        byteBuffer.clear();
                    }
                    sc.close();
                }
            }
        }
    }
}
