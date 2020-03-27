package advanced.serializable;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author lmc
 * @date 2020/3/27 10:29
 */

/*
 * 远程对象必须实现java.rmi.server.UniCastRemoteObject类，这样才能保证客户端访问获得远程对象时，
 * 该远程对象将会把自身的一个拷贝以Socket的形式传输给客户端，此时客户端所获得的这个拷贝称为“存根”，
 * 而服务器端本身已存在的远程对象则称之为“骨架”。其实此时的存根是客户端的一个代理，用于与服务器端的通信，
 * 而骨架也可认为是服务器端的一个代理，用于接收客户端的请求之后调用远程方法来响应客户端的请求。
 */

/* java.rmi.server.UnicastRemoteObject构造函数中将生成stub和skeleton */
public class RemoteInterfaceImpl extends UnicastRemoteObject implements RemoteInterface {
    private static final long serialVersionUID = 4077329331699640331L;
    // 这个实现必须有一个显式的构造函数，并且要抛出一个RemoteException异常
    protected RemoteInterfaceImpl() throws RemoteException {
        super();
    }

    @Override
    public String sayHello(String name) throws RemoteException {
        return "Hello " + name + " ^_^ ";
    }

}
