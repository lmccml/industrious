package advanced.io.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.InterruptibleChannel;
import java.nio.file.StandardOpenOption;

/**
 * @author lmc
 * @date 2020/1/3 16:09
 */
public class ChannelTest {
    public static void main(String[] args) throws Exception {
        String project_path = System.getProperty("user.dir");
        File file = new File(project_path + "/file/test.txt");
        File out_file = new File(project_path + "/file/out.txt");

        FileInputStream fileInputStream = new FileInputStream(file);
        FileOutputStream fileOutputStream = new FileOutputStream(out_file, false);

        //FileChannel它总是运行在阻塞模式下
        FileChannel fileInChannel = fileInputStream.getChannel();
        System.out.println(fileInChannel.size());
        //synchronized同步的，错误的写法，这样会置空fileInChannel，因为fileOutChannel为空，返回的fileInChannel为最新的fileOutChannel
        //FileChannel fileOutChannel = new FileOutputStream(file).getChannel();
        //System.out.println(fileInChannel.size());

        ByteBuffer byteBuffer = ByteBuffer.allocate(30);
        fileInChannel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array(), "UTF-8"));

        //byteBuffer.flip();//就会出错 容量会变成之前实际内容的刻度到起点的大小
        byteBuffer.rewind();
        byteBuffer.put("new start! 慎独".getBytes("UTF-8"));
        FileChannel fileOutChannel = fileOutputStream.getChannel();
        byteBuffer.position(0); //将byteBuffer的remaining内容写入通道，和positiony有关
        //注意这个方法是synchronized的
        fileOutChannel.write(byteBuffer);

        FileChannel fileInChannelNew = new FileInputStream(out_file).getChannel();
        ByteBuffer byteBufferNew = ByteBuffer.allocate(10);
        ByteBuffer byteBufferNew2 = ByteBuffer.allocate(10);
        ByteBuffer[] byteBufferArray = new ByteBuffer[]{byteBufferNew, byteBufferNew2};
        fileInChannelNew.read(byteBufferArray, 0 , 1); //指的是数组的下标，2指的数组长度
        System.out.println(new String(byteBufferArray[0].array(), "UTF-8"));
        System.out.println(new String(byteBufferArray[1].array(), "UTF-8"));

        FileChannel fileChannelSrc = new FileInputStream(file).getChannel();
        FileChannel fileChannelDst = new FileOutputStream(out_file).getChannel();
        //不用换用，直接一个通道向另一个通道传输字节流
        fileChannelSrc.transferTo(0, fileChannelSrc.size(), fileChannelDst);

        //RandomAccessFile类是一个专门读写文件的类，封装了基本的IO流，在读写文件内容方面比常规IO流更方便、更灵活。但也仅限于读写文件，无法像IO流一样，可以传输内存和网络中的数据。
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");

        FileChannel fileChannel = FileChannel.open(file.toPath(), StandardOpenOption.READ);







    }
}
