package advanced.singleton;

/**
 * @author lmc
 * @date 2020/3/16 17:08
 */
public class SafeDoubleCheckedLocking {
    /*
    实例化对象的那行代码（标记为error的那行），实际上可以分解成以下三个步骤：
    分配内存空间
    初始化对象
    将对象指向刚分配的内存空间
    但是有些编译器为了性能的原因，可能会将第二步和第三步进行重排序，顺序就成了：
    分配内存空间
    将对象指向刚分配的内存空间
    初始化对象
    现在考虑重排序后，两个线程发生了以下调用
     */
    //加上volatile就是为了防止重新排序
    private volatile static SafeDoubleCheckedLocking uniqueSingleton;

    private SafeDoubleCheckedLocking() {
    }

    public SafeDoubleCheckedLocking getInstance() {
        if (null == uniqueSingleton) {
            synchronized (SafeDoubleCheckedLocking.class) {
                if (null == uniqueSingleton) {
                    uniqueSingleton = new SafeDoubleCheckedLocking();
                }
            }
        }
        return uniqueSingleton;
    }
}
