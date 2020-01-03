package io.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

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
        FileOutputStream fileOutputStream = new FileOutputStream(out_file, true);

        //FileChannel它总是运行在阻塞模式下
        FileChannel fileInChannel = fileInputStream.getChannel();
        System.out.println(fileInChannel.size());
        //synchronized同步的，错误的写法，这样会置空fileInChannel，因为fileOutChannel为空，返回的fileInChannel为最新的fileOutChannel
        //FileChannel fileOutChannel = new FileOutputStream(file).getChannel();
        //System.out.println(fileInChannel.size());

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
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
        ByteBuffer byteBufferNew = ByteBuffer.allocate(1024*1024);
        fileInChannelNew.read(byteBufferNew);
        System.out.println(new String(byteBufferNew.array(), "UTF-8"));


    }
}
