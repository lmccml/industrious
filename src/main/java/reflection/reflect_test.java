package reflection;

import practice.class_loader.get_class_obj;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class reflect_test {

    public static void main(String[] args) throws Exception {
        //项目内获取Class对象四种方式
        get_class_obj get_class_obj = new get_class_obj();
        //项目内获取Class对象四种方式

        //----------------------获取私有构造方法，并调用--------------------------------
        Class clazz_first = Class.forName("reflection.test_class");//注意此字符串必须是真实路径，就是带包名的类路径，包名.类名
        //public Constructor getConstructor(Class... parameterTypes):
        //获取单个的"公有的"构造方法：
        //public Constructor getDeclaredConstructor(Class... parameterTypes):
        //获取"某个构造方法"可以是私有
        Constructor con = clazz_first.getDeclaredConstructor();
        //1>、因为是无参的构造方法所以类型是一个null,不写也可以：这里需要的是一个参数的类型，切记是类型
        //2>、返回的是描述这个无参构造函数的类对象。
        System.out.println(con);
        //调用构造方法
        con.setAccessible(true);//暴力访问(忽略掉访问修饰符)
        Object obj_first = con.newInstance();
        test_class test_class_first = (test_class)obj_first;
        System.out.println(test_class_first.getId());

        //------------------------------获取私有字段****并调用-------------------------------------------
        Class clazz_second = Class.forName("reflection.test_class");//注意此字符串必须是真实路径，就是带包名的类路径，包名.类名
        //2.获取字段
        Field f = clazz_second.getDeclaredField("name");
        //3.获取一个对象
        Object obj_second = clazz_second.getConstructor().newInstance();//产生Student对象--》Student stu = new Student();
        //4.为字段设置值
        f.setAccessible(true);//暴力反射，解除私有限定
        f.set(obj_second, "刘德华");//为Student对象中的name属性赋值--》stu.name = "刘德华"
        //验证
        test_class test_class_second = (test_class)obj_second;
        System.out.println("验证姓名：" + test_class_second.getName());

        //--------------------------------------获取私有的方法并调用--------------------------------------------------
        //1.获取Class对象
        Class clazz_third = Class.forName("reflection.test_class");//注意此字符串必须是真实路径，就是带包名的类路径，包名.类名
        //2.获取方法
        Method method = clazz_third.getDeclaredMethod("setName", String.class);
        //3.获取一个对象
        Object obj_third = clazz_third.getConstructor().newInstance();//产生Student对象--》Student stu = new Student();
        //4.为字段设置值
        method.setAccessible(true);//暴力反射，解除私有限定
        method.invoke(obj_third,"刘德华");//为test_class对象中的name属性赋值--》test_class.name = "刘德华"
        //验证
        test_class test_class_third  = (test_class)obj_third;
        System.out.println("验证姓名：" + test_class_third.getName());

        //-----------------------------------------------反射main方法------------------------------------------------------------
        try {
            //1、获取Student对象的字节码
            Class clazz = Class.forName("reflection.test_class");

            //2、获取main方法
            Method methodMain = clazz.getMethod("main", String[].class);//第一个参数：方法名称，第二个参数：方法形参的类型，
            //3、调用main方法
            // methodMain.invoke(null, new String[]{"a","b","c"});
            //第一个参数，对象类型，因为方法是static静态的，所以为null可以，第二个参数是String数组，这里要注意在jdk1.4时是数组，jdk1.5之后是可变参数
            //这里拆的时候将  new String[]{"a","b","c"} 拆成3个对象。。。所以需要将它强转。
            methodMain.invoke(null, (Object) new String[]{"a", "b", "c"});//方式一
            // methodMain.invoke(null, new Object[]{new String[]{"a","b","c"}});//方式二

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
