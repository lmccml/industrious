package advanced.class_loader;

import advanced.serializable.custom_serializable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;

/**
 * @author lmc
 * @date 2020/4/16 15:38
 */
public class newInstanceTest {
    /*
    创建对象的方式有四种：
    用new关键字创建
    调用对象的clone方法
    利用反射，调用Class类的或者是Constructor类的newInstance（）方法
    用反序列化，调用ObjectInputStream类的readObject（）方法
     */
    public static void main(String[] args) throws Exception,CloneNotSupportedException{
        test_class test1 = new test_class();
        System.out.println(test1.getId());

        Class clazz = test_class.class;
        test_class test2 = (test_class)clazz.newInstance();
        System.out.println(test2.getName());

        Constructor constructor = clazz.getConstructor();
        test_class test22 = (test_class) constructor.newInstance();
        System.out.println(test22.getName());

        test_class test3 = test1.clone();
        System.out.println(test3.getAge());

        //序列化
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./file/test_class_obj.txt"));
        //调用我们自定义的writeObject()方法
        out.writeObject(test1);
        out.close();

        //反序列化
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("./file/test_class_obj.txt"));
        //调用自定义的readObject()方法
        test_class test4 = (test_class) in.readObject();
        System.out.println(test4.getId());
        in.close();




    }
}
