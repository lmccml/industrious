package advanced.io.nio;

/**
 * @author lmc
 * @date 2020/1/2
 */
public class test implements AutoCloseable {
    int order;
    test(int order) {
        this.order = order;
    }
    public static void main(String[] args) {
        try (test test = new test(1); test test2 = new test(2)){
            //不是try-with-resource 结构，不会被调用
            test test3 = new test(3);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        System.out.println(order + "在try-with-resource结构中我会被自动调用！" );
    }
}
