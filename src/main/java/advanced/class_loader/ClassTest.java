package advanced.class_loader;

import java.lang.reflect.Field;

/**
 * @author lmc
 * @date 2020/1/10 11:48
 */
public class ClassTest {

    //任何小写class定义的类，有一个魔法属性：class，来获取此类的大写Class类的对象
    private static Class<One> one = One.class;
    private static Class<Another> another = Another.class;

    public static void main(String[] args) throws Exception {
        One oneObj = one.newInstance();
        oneObj.call();
        Another anotherObj = another.newInstance();
        anotherObj.speak();

        Field field = one.getDeclaredField("innerStr");
        field.setAccessible(true);
        field.set(oneObj, "new str!");
        System.out.println(oneObj.getInnerStr());

    }

}
