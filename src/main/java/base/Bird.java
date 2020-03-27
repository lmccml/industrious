package base;

/**
 * @author lmc
 * @date 2020/3/27 9:55
 */
public abstract class Bird {
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public abstract int fly();
}
