package advanced.io.socket;

import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

/**
 * @author lmc
 * @date 2020/1/7 11:57
 */
public class UDPServer {
    public static void main(String[] args) throws Exception {
        DatagramSocket datagramSocket = new DatagramSocket(8888);
        byte[] bytes = new byte[12];
        DatagramPacket datagramPacket = new DatagramPacket(bytes, 10);
        datagramSocket.receive(datagramPacket);
        datagramSocket.close();
        System.out.println(new String(datagramPacket.getData()));
    }
}
