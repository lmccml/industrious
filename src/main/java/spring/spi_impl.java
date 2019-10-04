package spring;

/*
优点：
使用Java SPI机制的优势是实现解耦，使得第三方服务模块的装配控制的逻辑与调用者的业务代码分离，而不是耦合在一起。应用程序可以根据实际业务情况启用框架扩展或替换框架组件。

缺点：
虽然ServiceLoader也算是使用的延迟加载，但是基本只能通过遍历全部获取，也就是接口的实现类全部加载并实例化一遍。
如果你并不想用某些实现类，它也被加载并实例化了，这就造成了浪费。
获取某个实现类的方式不够灵活，只能通过Iterator形式获取，不能根据某个参数来获取对应的实现类。
多个并发多线程使用ServiceLoader类的实例是不安全的。

 */
public class spi_impl implements spi_interface {
    @Override
    public void sout() {
        System.out.println("这是一个spi的实现");
    }
}


