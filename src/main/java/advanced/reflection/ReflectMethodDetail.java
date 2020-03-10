package advanced.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author lmc
 * @date 2020/3/10 15:59
 */
public class ReflectMethodDetail {
    public static void main(String[] args) throws Exception {
        test_class testClassInstance = new test_class();

        //项目内获取Class对象四种方式
        //initialize为true表示会进行初始化initialization步骤，即静态初始化（会初始化类变量，静态代码块）。
        Class testClass1 = ClassLoader.getSystemClassLoader().loadClass("advanced.reflection.test_class");
        Class testClass2 = test_class.class;
        //这2种不会对类进行初始化。

        Class testClass3 = Class.forName("advanced.reflection.test_class");
        Class testClass4 = testClassInstance.getClass();

        //返回本类声明的所有字段，包括非public的， 但不包括父类的
        Field field = testClass1.getDeclaredField("age");
        //返回本类或父类中指定名称的public字段，找不到抛出异常NoSuchFieldException
        Field field2 = testClass1.getField("name");
        int mods = field.getModifiers();
        System.out.println(Modifier.toString(mods));
        System.out.println(Modifier.isPublic(mods));

        String[] stringClass = new String[]{"1", "2"};
        //获取数组的元素类型
        Class classType = stringClass.getClass().getComponentType();
        System.out.println(classType);



    }
}
