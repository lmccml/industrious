package advanced.io.nio;

import sun.nio.ch.DirectBuffer;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lmc
 * @date 2020/1/3 10:21
 */
public class DirectByteBufferTest {
    public static void main(String[] args) throws Exception {
        //-XX:MaxDirectMemorySize=512m
        System.out.println(Runtime.getRuntime().maxMemory()/1024/1024);//默认等于可用的最大Java堆大小-Xmx
        ByteBuffer buffer = null;
        List<ByteBuffer> list = new ArrayList<>();
        for(int i = 0; i < 10000; i++){
            buffer = ByteBuffer.allocateDirect(1024 * 1024 * 15);
            //保持有引用,超过大Java堆大小-Xmx就会报Exception in thread "main" java.lang.OutOfMemoryError: Direct buffer memory
            list.add(buffer);
            //及时清除就不会报错，但是如果禁用fullGC -XX:-+DisableExplicitGC -XX:MaxDirectMemorySize=51200m会撑爆服务器
            clean(buffer);
        }

    }

    public static void clean(final ByteBuffer byteBuffer) {
        if (byteBuffer.isDirect()) {
            ((DirectBuffer) byteBuffer).cleaner().clean();
        }
    }

}
