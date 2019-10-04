package class_loader;

/*
ContextClassLoader「线程上下文类加载器」，这究竟是什么东西？

首先 contextClassLoader 是那种需要显示使用的类加载器，如果你没有显示使用它，也就永远不会在任何地方用到它。你可以使用下面这种方式来显示使用它

Thread.currentThread().getContextClassLoader().loadClass(name);

这意味着如果你使用 forName(string name) 方法加载目标类，它不会自动使用 contextClassLoader。那些因为代码上的依赖关系而懒惰加载的类也不会自动使用 contextClassLoader来加载。

其次线程的 contextClassLoader 是从父线程那里继承过来的，所谓父线程就是创建了当前线程的线程。程序启动时的 main 线程的 contextClassLoader 就是 AppClassLoader。这意味着如果没有人工去设置，那么所有的线程的 contextClassLoader 都是 AppClassLoader。

那这个 contextClassLoader 究竟是做什么用的？我们要使用前面提到了类加载器分工与合作的原理来解释它的用途。

它可以做到跨线程共享类，只要它们共享同一个 contextClassLoader。父子线程之间会自动传递 contextClassLoader，所以共享起来将是自动化的。

如果不同的线程使用不同的 contextClassLoader，那么不同的线程使用的类就可以隔离开来。

如果我们对业务进行划分，不同的业务使用不同的线程池，线程池内部共享同一个 contextClassLoader，线程池之间使用不同的 contextClassLoader，就可以很好的起到隔离保护的作用，避免类版本冲突。

如果我们不去定制 contextClassLoader，那么所有的线程将会默认使用 AppClassLoader，所有的类都将会是共享的。

线程的 contextClassLoader 使用场合比较罕见，如果上面的逻辑晦涩难懂也不必过于计较。
 */
public class context_class_loader_test {
    public static void main(String[] args) throws Exception {
        test_class test_class = (test_class)Thread.currentThread().getContextClassLoader().loadClass("class_loader.test_class").newInstance();
        System.out.println(test_class.getId());
    }
}
