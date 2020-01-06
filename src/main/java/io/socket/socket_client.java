package io.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class socket_client {

    public static void main(String[] args) {
        BufferedReader burd_first = null;
        BufferedReader burd_second = null;
        String cmd = null;
        PrintWriter pw = null;
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1",9000);

            //写
            burd_second = new BufferedReader(new InputStreamReader(System.in));
            while ((cmd = burd_second.readLine()) != null){
                //使用PrintWriter从socket写入字符串(写socket输出流)
                pw = new PrintWriter(socket.getOutputStream(), true);
                pw.println(cmd);
                //使用BufferedReader从socket读取字符串(读socket输入流)
                burd_first = new BufferedReader(new InputStreamReader((socket.getInputStream())));
                String s = burd_first.readLine();
                if ("bye".equalsIgnoreCase(s)) {
                    break;
                }
                System.out.println(s);
            }

        }catch (Exception e){
            System.out.println("这个环节出现了一个异常，需要及时处理"+e);
        }finally {
            stream_util.close(burd_first);
            stream_util.close(burd_second);
            stream_util.close(socket);
        }
        System.out.println("bye");
    }
}
