package advanced.class_loader;

/*
.java文件被编译之后会产生.class文件。这是一个二进制文件，其内容是只有jvm虚拟机能识别的字节码。
jvm虚拟机将.class文件中的字节码装载在内存中，解析并生成对应的Class对象
当一个类被装载过后，java虚拟机会自动产生一个Class对象.一个被成功装载的类，有且只有一个Class对象与之对应
项目内获取Class对象四种方式
 */
public class get_class_obj {
    public static void main(String[] args) throws Exception {
        Class<?> clazz_first = null;
        clazz_first = test_class.class;

        Class<?> clazz_second = null;
        test_class tc = new test_class();
        clazz_second =  tc.getClass();

        Class<?> clazz_third = null;
        clazz_third = Class.forName("advanced.class_loader.test_class");

        //这两个方法都可以用来加载目标类，它们之间有一个小小的区别，那就是 Class.forName() 方法可以获取原生类型的 Class，而 ClassLoader.loadClass() 则会报错。
        Class<?> clazz_fourth = null;
        clazz_fourth = get_class_obj.class.getClassLoader().loadClass("advanced.class_loader.test_class");

        test_class test_class = (test_class)clazz_first.newInstance();
        System.out.println(test_class.getId());

        test_class = (test_class)clazz_second.newInstance();
        System.out.println(test_class.getName());

        test_class = (test_class)clazz_third.newInstance();
        System.out.println(test_class.getAge());

        test_class = (test_class)clazz_fourth.newInstance();
        test_class.preDestroy();


    }


}
