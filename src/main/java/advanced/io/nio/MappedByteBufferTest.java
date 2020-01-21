package advanced.io.nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author lmc
 * @date 2020/1/6 9:44
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws Exception {
        String project_path = System.getProperty("user.dir");
        File file = new File(project_path + "/file/mapper.txt");

        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        FileChannel fileChannel = raf.getChannel();

        //传统的基于文件流的方式读取文件方式是系统指令调用，文件数据首先会被读取到进程的内核空间的缓冲区，而后复制到进程的用户空间，这个过程中存在两次数据拷贝；
        // 而内存映射方式读取文件的方式，也是系统指令调用，在产生缺页中断后，CPU直接从磁盘文件load数据到进程的用户空间，只有一次数据拷贝。
        String a = "我要写入文件";
        MappedByteBuffer mbb = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, a.getBytes().length+1);
        mbb.put(a.getBytes());
        mbb.flip();

        byte[] bb = new byte[mbb.capacity()];
        while (mbb.hasRemaining()){
            byte b = mbb.get();
            bb[mbb.position()]=b;
        }
        System.out.println(new String(bb));
        raf.close();
    }
}
