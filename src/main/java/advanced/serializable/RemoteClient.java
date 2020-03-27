package advanced.serializable;

import java.rmi.Naming;

/**
 * @author lmc
 * @date 2020/3/27 10:33
 */
public class RemoteClient {
    public static void main(String[] args) {
        try {
            /* 从RMI Registry中请求stub
             * 如果RMI Service就在本地机器上，URL就是：rmi://localhost:8088/hello
             * 否则，URL就是：rmi://RMIService_IP:8088/hello
             */
            RemoteInterface hello = (RemoteInterface) Naming.lookup("rmi://localhost:8088/hello");
            /* 通过stub调用远程接口实现 */
            System.out.println(hello.sayHello("remote call!"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
