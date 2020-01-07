package io.socket;

import java.net.*;
import java.util.Enumeration;
import java.util.List;

/**
 * @author lmc
 * @date 2020/1/7 12:03
 */
public class UDPClient {
    public static void main(String[] args) throws Exception {
//        DatagramSocket datagramSocket = new DatagramSocket();
//        datagramSocket.connect(new InetSocketAddress("localhost", 8888));
//        String send_str = "testlmc120";
//        DatagramPacket datagramPacket = new DatagramPacket(send_str.getBytes(), 10);
//        datagramSocket.send(datagramPacket);
//        datagramSocket.close();

        //广播
        DatagramSocket datagramSocketBroadCast = new DatagramSocket();
        datagramSocketBroadCast.setBroadcast(true);
        datagramSocketBroadCast.connect(InetAddress.getByName(new UDPClient().getBroadCastAddr()), 8888);
        String send_any_str = "testlmc120";
        DatagramPacket datagramPacket2 = new DatagramPacket(send_any_str.getBytes(), 10);
        datagramSocketBroadCast.send(datagramPacket2);
        datagramSocketBroadCast.close();


    }

    //获取广播地址
    public String getBroadCastAddr()throws Exception {
        Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
        while (enumeration.hasMoreElements()) {
            NetworkInterface networkInterface = enumeration.nextElement();
            if ("Realtek PCIe GbE Family Controller".equalsIgnoreCase(networkInterface.getDisplayName())) {
                List<InterfaceAddress> list = networkInterface.getInterfaceAddresses();
                for (int i = 0; i < list.size(); i++) {
                    InterfaceAddress interfaceAddress = list.get(i);
                    InetAddress broadCast = interfaceAddress.getBroadcast();
                    if (null != broadCast) {
                        return broadCast.getHostAddress();
                    }
                }
            }
        }
        return "";
    }
}
