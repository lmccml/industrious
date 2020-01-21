package advanced.io.nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;

/**
 * @author lmc
 * @date 2020/1/2 16:55
 */
public class BufferAPIStudy {
    public static void main(String[] args) throws Exception{
        ByteBuffer byteBuffers = ByteBuffer.wrap(new byte[] {1, 3, 5, 7, 8});
        IntBuffer intBuffer = IntBuffer.wrap(new int[] {1, 3, 5});
        CharBuffer charBuffer = CharBuffer.wrap(new char[] {'a', 'b', 'c'});

        System.out.println(byteBuffers.capacity());//5
        System.out.println(byteBuffers.limit());//5
        System.out.println(byteBuffers.position());//0
        System.out.println(byteBuffers.mark());//java.nio.HeapByteBuffer[pos=0 lim=5 cap=5]

        byteBuffers.position(3);
        byteBuffers.limit(2);//下标0-1可读可写，其它的不可访问
        System.out.println(byteBuffers.position());//2  position会变成limit值
        byte b = 6;
        byteBuffers.put(1, b); // >=2则报java.lang.IndexOutOfBoundsException
        System.out.println(byteBuffers.get(1)); //6 >=2则报java.lang.IndexOutOfBoundsException

        byteBuffers.position(1);
        byte bb = 66;
        byteBuffers.put(bb);//position会加1
        System.out.println(byteBuffers.get(0));//1
        System.out.println(byteBuffers.get(1));//66
        byteBuffers.position(0);
        System.out.println(byteBuffers.get());//1  //position会加1
        System.out.println(byteBuffers.get());//66 //position会加1
        byte bbb = 88;
        //byteBuffers.put(bbb); //异常，此时下标index=2报java.lang.IndexOutOfBoundsException

        System.out.println(byteBuffers.position());//2
        byteBuffers.mark();
        byteBuffers.position(1);
        //byteBuffers.reset();//因为mark>position 报InvalidMarkException
        byteBuffers.position(0);
        byteBuffers.mark();
        byteBuffers.position(1);
        byteBuffers.reset();
        System.out.println(byteBuffers.position());//0

        byteBuffers.position(1);
        byteBuffers.mark();
        byteBuffers.position(0);
        //byteBuffers.reset();
        //System.out.println(byteBuffers.position());//0 position调整小于mark=1的值，则1将丢弃，mark是2个position中间标志

        ByteBuffer direct_buffer = ByteBuffer.allocateDirect(1000);
        System.out.println(direct_buffer.isDirect());//true
        System.out.println(byteBuffers.isDirect());//false

        byteBuffers.position(1);
        System.out.println(byteBuffers.limit()); //2
        byteBuffers.flip();
        System.out.println(byteBuffers.position()); //0,postion = 0
        System.out.println(byteBuffers.limit());  //1, 2->1 limit=old_postion, mark = -1

        byteBuffers.position(1);
        byteBuffers.limit(2);
        byteBuffers.rewind();
        System.out.println(byteBuffers.position()); //0, postion = 0
        System.out.println(byteBuffers.limit());  //2, limit = limit, mark = -1

        ByteBuffer byteBufferSlice = byteBuffers.slice();
        ByteBuffer byteBufferDuplicate = byteBuffers.duplicate();
        System.out.println(byteBuffers.capacity()); //5
        System.out.println(byteBufferDuplicate.capacity()); //5
        System.out.println(byteBufferSlice.capacity());  //2  capacity=remaining=limit-postion
        byteBufferSlice.put((byte) 3);
        System.out.println(byteBuffers.get()); //3, 内容公用，但是postion，limit，mark是各一套
        byteBufferSlice.limit(1);
        System.out.println(byteBuffers.limit()); //2, 内容公用，但是postion，limit，mark是各一套

        //扩容
        ByteBuffer extendByteBuffer = ByteBuffer.allocate(byteBuffers.capacity() * 2);
        extendByteBuffer.put(byteBuffers);
        System.out.println(extendByteBuffer.limit());  //10
        System.out.println(extendByteBuffer.position()); //进行了put操作，postion+1

    }
}
