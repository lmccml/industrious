package genericity;

public class son extends father {
    int id;

    //默认无参构造方法
    public son() {
        //不写，默认调用这个的
        super();
    }

    public son(int id) {
        //必须第一行
        this();
        this.id = id + super.id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
