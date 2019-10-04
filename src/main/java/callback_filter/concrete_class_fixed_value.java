package callback_filter;

import org.springframework.cglib.proxy.FixedValue;
/*
该类实现FixedValue接口，同时锁定回调值为999(整型，CallbackFilter中定义的使用FixedValue型回调的方法为getConcreteMethodFixedValue，该方法返回值为整型)。
 */
public class concrete_class_fixed_value implements FixedValue {

    public Object loadObject() throws Exception {

        System.out.println("ConcreteClassFixedValue loadObject ...");

        Object object = 999;

        return object;

    }

}
