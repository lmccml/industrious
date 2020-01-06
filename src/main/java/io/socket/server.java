package io.socket;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author lmc
 * @date 2020/1/6 15:13
 */
public class server {
    public static void main(String[] args) throws Exception{
        //backlog允许的连接数(默认backlog = 50;)
        //java.net.ConnectException: Connection refused: connect
        //java.net.ConnectException: Connection refused: connect
        //java.net.ConnectException: Connection refused: connect
        ServerSocket serverSocket = new ServerSocket(8888, 50000);
        //必须使用while循环，每次循环阻塞在accept函数，等待新的连接到来，这样才能返回新的socket。
        // 如果不使用while死循环每次阻塞在accept函数，也可以面向多个客户连接，此时将在socket抽象层，自动建立socket，并且该socket不受控制
        //必须使用数组（或其他数据结构）保存当前accept创建的socket，否则下次新的socket建立后，当前的socket将不受控制。
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println(socket.hashCode());
            InputStreamReader reader = new InputStreamReader(socket.getInputStream());
            char[] buf = new char[1024];
            int num = 0;
            while((num = reader.read(buf)) != -1) {
                System.out.println(new String(buf,0, num));
            }
            //serverSocket将会close
            //socket.close();
        }
    }
}
