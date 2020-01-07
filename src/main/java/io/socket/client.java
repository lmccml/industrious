package io.socket;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author lmc
 * @date 2020/1/6 15:13
 */
public class client {
    public static void main(String[] args) throws Exception{
        for(int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        new client().testSocket();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            System.out.println(i);
        }

    }

    void testSocket() throws Exception {
        //Socket socket = new Socket("localhost", 8888);
        Socket socket = new Socket();
        //socket.bind(new InetSocketAddress("localhost", 17255));
        socket.connect(new InetSocketAddress("localhost", 8888));

        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        printWriter.write("tesregegegeg");
        printWriter.write("tesregegegege3");
        Thread.sleep(1000);
        printWriter.close();
        socket.close();

    }
}
