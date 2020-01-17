package base;

/**
 * @author lmc
 * @date 2020/1/16 10:10
 */
public interface Java8Interface {
    default void helloWorld() {
        System.out.println("hello World!");
    }

    default void hi() {
        System.out.println("hi i'm from Java8Interface");
    }


    static void pp() {
        System.out.println(123);
    }

    public static void main(String[] args) throws Exception {
        Java8Interface.pp();
        Java8Interface java8Interface = new Java8InterfaceImpl();
        java8Interface.hi();
        java8Interface.helloWorld();
    }

    public class Java8InterfaceImpl implements Java8Interface {

    }


}
