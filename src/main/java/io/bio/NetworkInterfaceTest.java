package io.bio;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author lmc
 * @date 2020/1/6 11:13
 */
public class NetworkInterfaceTest {
    public static void main(String[] args) throws Exception {
        Enumeration<NetworkInterface> networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaceEnumeration.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaceEnumeration.nextElement();
            System.out.println(networkInterface.getDisplayName());
            System.out.println(networkInterface.isLoopback());
        }

        InetAddress inetAddress = InetAddress.getLocalHost();
        System.out.println(inetAddress.toString());
    }
}
