package io.socket;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * @author lmc
 * @date 2020/1/7 14:13
 */
public class MulticastSocketServer {
    public static void main(String[] args) throws Exception {
        MulticastSocket multicastSocket = new MulticastSocket(8888);
        multicastSocket.joinGroup(InetAddress.getByName("224.0.0.1"));
        byte[] bytes = new byte[12];
        DatagramPacket datagramPacket = new DatagramPacket(bytes, 10);
        multicastSocket.receive(datagramPacket);
        multicastSocket.close();
        System.out.println(new String(datagramPacket.getData()));
    }
}
