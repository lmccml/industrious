package effective_java;

public class override_interface implements interface_test {
    /*
      @Override会检测你的实现类有没有按照父类定义的方法重写，如果拼写错或者格式不正确，这个报错是友好的
     */
    //@Override
    public void Test(String aa) {

    }

    @Override
    public void test() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("aa");
            }
        }).run();

    }

    public static void main(String[] args) {

    }
}
