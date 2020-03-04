package base;

/**
 * @author lmc
 * @date 2020/3/4 14:28
 */
public class SonClass extends FatherClass {
    public SonClass() {
        System.out.println("SonClass init!");
    }

    public static void main(String[] args) {
        SonClass sonClass = new SonClass();
    }
}
