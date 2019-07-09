package practice.callback_filter;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import practice.dynamic_proxy.service_demo_impl;

import java.lang.reflect.Method;

/*
CGLIB创建的动态代理对象在性能上要比jdk高效一些，但是创建过程时间想对长一些，对于一次创建多次使用的对象，CGLIB更合适，反之则jdk动态代理
 */
public class cglib_proxy implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Exception,Throwable {
        //代理类调用
        methodProxy.invokeSuper(o, args);
        //由于性能的原因，对原始方法的调用使用CGLIB的net.sf.cglib.proxy.MethodProxy对象，而不是反射中一般使用java.lang.reflect.Method对象。
        //method.invoke(o, args);
        return null;
    }

}
