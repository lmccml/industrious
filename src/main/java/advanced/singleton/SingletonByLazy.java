package advanced.singleton;

/**
 * @author lmc
 * @date 2020/5/13 22:24
 */
public class SingletonByLazy {
    private static SingletonByLazy singletonByLazy;

    private SingletonByLazy(){

    }

    //存在线程安全问题。在并发获取实例的时候，可能会存在构建了多个实例的情况。
    public SingletonByLazy getInstance(){
        if(singletonByLazy == null){
            singletonByLazy = new SingletonByLazy();
        }
        return singletonByLazy;
    }

}
