package advanced.io.socket;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

/**
 * @author lmc
 * @date 2020/1/6 17:01
 */
public class InetSocketAddressTest {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket();
        //bind之前开启端口复用
        serverSocket.setReuseAddress(true);
        InetAddress inetAddress = InetAddress.getByName("192.168.3.12");
        InetAddress inetAddress2 = InetAddress.getByName("localhost");
        InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress, 8888);
        InetSocketAddress inetSocketAddress2 = new InetSocketAddress(inetAddress2, 8889);
        InetSocketAddress inetSocketAddress3 = new InetSocketAddress("192.168.3.12", 8890);
        serverSocket.bind(inetSocketAddress);

        System.out.println(inetSocketAddress.getHostName());
        System.out.println(inetSocketAddress.getHostName());
        System.out.println(inetSocketAddress.getHostString());
        System.out.println(inetSocketAddress2.getHostName());
        System.out.println(inetSocketAddress2.getHostString());
        System.out.println(inetSocketAddress3.getHostName());
        System.out.println(inetSocketAddress3.getHostString());

        System.out.println(inetSocketAddress.getAddress().getHostAddress());

        //是否可以端口复用
        System.out.println(serverSocket.getReuseAddress());
    }
}
