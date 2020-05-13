package advanced.singleton;

/**
 * @author lmc
 * @date 2020/5/13 22:32
 */
public enum SingletonByEnum {
    /*通过反编译后代码我们可以看到，public final class T extends Enum，说明，该类是继承了Enum类的，
    同时final关键字告诉我们，这个类也是不能被继承的。当我们使用enmu来定义一个枚举类型的时候，
    编译器会自动帮我们创建一个final类型的类继承Enum类,所以枚举类型不能被继承，我们看到这个类中有几个属性和方法。
    都是static类型的，因为static类型的属性会在类被加载之后被初始化，
    当一个Java类第一次被真正使用到的时候静态资源被初始化、Java类的加载和初始化过程都是线程安全的。所以，创建一个enum类型是线程安全的。
    */
    SINGLETON;
    void doSomething(){
        System.out.println("do something");
    }

    /*
    为什么反序列化枚举类型也不会创建新的实例？
    枚举类型在序列化的时候Java仅仅是将枚举对象的name属性输出到结果中，反序列化的时候则是通过java.lang.Enum的valueOf方法来根据名字查找枚举对象。
    同时，编译器是不允许任何对这种序列化机制的定制的，因此禁用了writeObject、readObject、readObjectNoData、writeReplace和readResolve等方法。
     */
    public static void main(String[] args) {
        SingletonByEnum.SINGLETON.doSomething();
    }
}
