package advanced.callback_filter;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.CallbackFilter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.NoOp;
import java.lang.reflect.Method;

/*
作用:
在CGLib回调时可以设置对不同方法执行不同的回调逻辑，或者根本不执行回调。
在JDK动态代理中并没有类似的功能，对InvocationHandler接口方法的调用对代理类内的所以方法都有效。
 */
public class call_back_test implements CallbackFilter {
    @Override
    public int accept(Method method) {
        if("getConcreteMethodB".equals(method.getName())){
            return 0;//Callback callbacks[0]
        }
        if("getConcreteMethodA".equals(method.getName())){
            return 1;//Callback callbacks[1]
        }
        if("getConcreteMethodFixedValue".equals(method.getName())){
            return 2;//Callback callbacks[2]
        }
        return 0;
    }

    public static void main(String[] args) {
        Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(need_to_be_proxyed.class);
        CallbackFilter filter = new call_back_test();
        enhancer.setCallbackFilter(filter);

        /*
        生成代理类前，设置了CallbackFilter，上文中ConcreteClassCallbackFilter实现类的返回值对应Callback[]数组中的位置索引。此处包含了CGLib中的3种回调方式：
        (1)MethodInterceptor：方法拦截器，上一篇文章中已经详细介绍过，此处不再赘述。
        (2)NoOp.INSTANCE：这个NoOp表示no operator，即什么操作也不做，代理类直接调用被代理的方法不进行拦截。
        (3)FixedValue：表示锁定方法返回值，无论被代理类的方法返回什么值，回调方法都返回固定值。
        其中，ConcreteClassFixedValue类实现如下
         */
        Callback interceptor = new cglib_proxy();//(1)
        Callback no_op = NoOp.INSTANCE;//(2)
        Callback concrete_class_fixed_value = new concrete_class_fixed_value();//(3)
        Callback[] callbacks = new Callback[]{interceptor, no_op, concrete_class_fixed_value};
        enhancer.setCallbacks(callbacks);
        need_to_be_proxyed proxy_object = (need_to_be_proxyed)enhancer.create();
        System.out.println("*** NoOp Callback ***");
        proxy_object.getConcreteMethodA("abcde");

        System.out.println("*** MethodInterceptor Callback ***");
        proxy_object.getConcreteMethodB(1);

        System.out.println("*** FixedValue Callback ***");
        int fixed1 = proxy_object.getConcreteMethodFixedValue(128);
        System.out.println("fixedValue1:"+fixed1);
        int fixed2 = proxy_object.getConcreteMethodFixedValue(256);
        System.out.println("fixedValue2:"+fixed2);
        /*
        getConcreteMethodA对应CallbackFilter中定义的索引1，在Callback[]数组中使用的过滤为NoOp,因此直接执行了被代理方法。
        getConcreteMethodB对应CallbackFilter中定义的索引0，在Callback[]数组中使用的过滤为MethodInterceptor，因此执行了方法拦截器进行拦截。
        getConcreteMethodFixedValue对应CallbackFilter中定义的索引2，在Callback[]数组中使用的过滤为FixedValue，因此2次赋值128和256的调用其结果均被锁定为返回999。
         */
    }
}
