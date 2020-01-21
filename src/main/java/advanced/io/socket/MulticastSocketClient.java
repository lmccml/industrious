package advanced.io.socket;

import java.net.*;
import java.util.concurrent.FutureTask;

/**
 * @author lmc
 * @date 2020/1/7 14:13
 */
public class MulticastSocketClient {
    public static void main(String[] args) throws Exception {
        MulticastSocket multicastSocket = new MulticastSocket();
        String send_str = "testlmc120";
        DatagramPacket datagramPacket = new DatagramPacket(send_str.getBytes(), 10, InetAddress.getByName("224.0.0.1"), 8888);
        multicastSocket.send(datagramPacket);
        multicastSocket.close();
    }

}
