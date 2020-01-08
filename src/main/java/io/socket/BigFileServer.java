package io.socket;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Set;

/**
 * @author lmc
 * @date 2020/1/8 10:12
 */
public class BigFileServer {
    public static void main(String[] args) throws Exception {
        Selector selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress("localhost", 8888));
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        SelectionKey sscSelectionKey = ssc.register(selector, SelectionKey.OP_ACCEPT);
        sscSelectionKey.attach(new String("我是附件"));
        boolean isRun = true;
        while (isRun) {
            if(selector.select(1000) == 0){
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            if(iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                System.out.println(selectionKey.attachment());
                iterator.remove();
                if(selectionKey.isValid() && selectionKey.isAcceptable()) {
                    SocketChannel socketChannel = ssc.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_WRITE);
                }
                if(selectionKey.isValid() && selectionKey.isWritable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    FileInputStream fileInputStream = new FileInputStream(Paths.get("E:\\node-three\\node-three.vmdk").toFile());
                    FileChannel fileChannel = fileInputStream.getChannel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(524288000);
                    while (fileChannel.position() < fileChannel.size()) {
                        fileChannel.read(byteBuffer);
                        byteBuffer.flip();
                        while (byteBuffer.hasRemaining()) {
                            socketChannel.write(byteBuffer);
                        }
                        byteBuffer.clear();
                        System.out.println(fileChannel.position());
                        System.out.println(fileChannel.size());
                    }
                    socketChannel.close();
                }
            }
        }
        ssc.close();
    }
}
