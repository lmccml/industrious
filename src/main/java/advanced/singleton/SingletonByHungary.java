package advanced.singleton;

/**
 * @author lmc
 * @date 2020/5/13 22:22
 */
public class SingletonByHungary {
    private static SingletonByHungary instance = new SingletonByHungary();

    private SingletonByHungary(){
    }

    public static SingletonByHungary getInstance() {
        return instance;
    }
}
