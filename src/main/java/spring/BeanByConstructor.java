package spring;

/**
 * @author lmc
 * @date 2020/3/27 16:49
 */
public class BeanByConstructor {
    private String id;
    BeanByConstructor(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
