package practice.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/*
动态代理类：在程序运行时，通过反射机制动态生成。
动态代理类通常代理接口下的所有类。
动态代理事先不知道要代理的是什么，只有在运行的时候才能确定。
动态代理的调用处理程序必须事先InvocationHandler接口，及使用Proxy类中的newProxyInstance方法动态的创建代理类。
Java动态代理只能代理接口，要代理类需要使用第三方的CLIGB等类库。

好处：
Java动态代理的优势是实现无侵入式的代码扩展，也就是方法的增强；
让你可以在不用修改源码的情况下，增强一些方法；
在方法的前后你可以做你任何想做的事情（甚至不去执行这个方法就可以）。
 */
public class jdk_proxy implements InvocationHandler {
    private final service_demo_impl service_demo_impl = new service_demo_impl();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        method.invoke(service_demo_impl, args);
        return null;
    }

    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("practice.dynamic_proxy.service_demo_impl");
        service_demo service_demo_proxy = (service_demo)Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] { practice.dynamic_proxy.service_demo.class} , new jdk_proxy());
        service_demo service_demo_proxy_second = (service_demo)Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces() , new jdk_proxy());
        service_demo_proxy.dosth();
        service_demo_proxy_second.dosth();
    }
}
