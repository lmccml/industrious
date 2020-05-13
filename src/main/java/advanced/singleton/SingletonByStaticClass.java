package advanced.singleton;

import java.lang.reflect.Constructor;

/**
 * @author lmc
 * @date 2020/5/13 22:29
 */
public class SingletonByStaticClass {
    private static class SingletonHolder {
        private static SingletonByStaticClass instance = new SingletonByStaticClass();
    }

    private SingletonByStaticClass() {

    }

    public static SingletonByStaticClass getInstance() {
        return SingletonHolder.instance;
    }

    /*
    静态内部类看起来已经是最完美的方法了，其实不是，可能还存在反射攻击或者反序列化攻击。
     */
    public static void main(String[] args) throws Exception {
        SingletonByStaticClass singleton = SingletonByStaticClass.getInstance();
        Constructor<SingletonByStaticClass> constructor = SingletonByStaticClass.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        SingletonByStaticClass newSingleton = constructor.newInstance();
        System.out.println(singleton == newSingleton);
    }
}
