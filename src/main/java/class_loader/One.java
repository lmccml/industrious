package class_loader;

/**
 * @author lmc
 * @date 2020/1/10 14:14
 */
public class One {
    public One() {

    }
    public String innerStr = "time files";
    public void call() {
        System.out.println("this is one!");
    }

    public String getInnerStr() {
        return innerStr;
    }
}
