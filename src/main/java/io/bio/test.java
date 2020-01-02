package io.bio;

import java.io.*;

/**
 * @author lmc
 * @date 2020/1/2
 */
public class test {
    public static void main(String[] args) throws Exception{
        File directory = new File("");//参数为空

        String project_path = System.getProperty("user.dir");
        String project_path2 = directory.getCanonicalPath();
        String class_path = test.class.getResource("").getPath();

        File file = new File(project_path + "/file/test.txt");
        System.out.println("file.exists " + file.exists());
        File out_file = new File(project_path + "/file/out.txt");
        Byte[] bytes = new Byte[4*1024];

        //InputStream 是字节输入流的所有类的超类,一般我们使用它的子类,如FileInputStream等.
        InputStream fis = new FileInputStream(file);
        int ch_fis = fis.read();
        while (ch_fis != -1) {
            System.out.print((char) ch_fis);
            ch_fis = fis.read();
        }
        System.out.print("\n");

        //处理字符流的抽象类
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
        InputStreamReader isr_copy = isr;
        int ch_isr = isr.read();
        while (ch_isr != -1) {
            System.out.print((char) ch_isr);
            ch_isr = isr.read();
        }
        System.out.print("\n");

        FileReader fr = new FileReader(file);
        char[] buf = new char[1024];
        int num = 0;
        while((num = fr.read(buf)) != -1) {
            System.out.println(new String(buf,0, num));
        }

        //OutputStream是字节输出流的所有类的超类,一般我们使用它的子类,如FileOutputStream等.
        OutputStream fos = new FileOutputStream(out_file);
        fos.write(new String("OutputStream write!").getBytes());
        fos.flush();

        //转换流，可以指定编码
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
        osw.write("OutputStreamWriter write!");
        osw.flush();

        //自动字节流写入
        PrintStream ps = new PrintStream(fos);
        ps.write(new String("PrintStream write!").getBytes());
        ps.flush();
        ps.close();

        //自动字符追加写入
        PrintWriter pw = new PrintWriter(new FileWriter(out_file, true));
        pw.write("pw write!");
        pw.flush();
        pw.close();

        fos.close();




    }
}
