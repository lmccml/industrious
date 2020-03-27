package advanced.serializable;

import java.rmi.Remote;

/**
 * @author lmc
 * @date 2020/3/27 10:28
 */
public interface RemoteInterface extends Remote {
    /* extends了Remote接口的类或者其他接口中的方法若是声明抛出了RemoteException异常，
     * 则表明该方法可被客户端远程访问调用。
     */
    public String sayHello(String name) throws java.rmi.RemoteException;
}
