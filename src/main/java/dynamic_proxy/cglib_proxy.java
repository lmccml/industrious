package dynamic_proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

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



    public static void main(String[] args) {
        //创建类加强器，用来创建动态代理类
        Enhancer eh = new Enhancer();
        /*
        void setSuperclass(java.lang.Class superclass) 设置产生的代理对象的父类。
        void setCallback(Callback callback) 设置CallBack接口的实例。
        void setCallbacks(Callback[] callbacks) 设置多个CallBack接口的实例。
        void setCallbackFilter(CallbackFilter filter) 设置方法回调过滤器。
        Object create() 使用默认无参数的构造函数创建目标对象。
        Object create(Class[], Object[]) 使用有参数的构造函数创建目标对象。参数Class[] 定义了参数的类型，第二个Object[]是参数的值。
        注意：在参数中，基本类型应被转化成类的类型。
        */
        eh.setSuperclass(service_demo_impl.class);
        eh.setCallback(new cglib_proxy());
        service_demo_impl service_demo_impl = (practice.dynamic_proxy.service_demo_impl) eh.create();
        service_demo_impl.dosth();
    }


}
