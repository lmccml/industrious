package base;

/**
 * @author lmc
 * @date 2020/3/27 9:50
 */
public class AbstractClassTest {
    public void test(Bird bird) {
        System.out.println(bird.getName() + "能够飞 " + bird.fly() + "米");
    }
    public static void main(String[] args) {
        AbstractClassTest test = new AbstractClassTest();
        //匿名内部类（要继承一个父类或者实现一个接口、直接使用new来生成一个对象的引用）
        test.test(new Bird() {
            @Override
            public int fly() {
                return 10000;
            }
            @Override
            public String getName() {
                return "大雁";
            }
        });
    }
}
