package base;

public class enum_to_use {

    //1.实战一无参
    enum SeasonType {
        SPRING, SUMMER, AUTUMN, WINTER;
        //可以不写
        SeasonType() {
            System.out.println("看看此构造函数被调用了几次");
        }
    }

    //2.实战二有一参
    enum SeasonType2 {
        // 通过构造函数传递参数并创建实例
        SPRING("spring"),
        SUMMER("summer"),
        AUTUMN("autumn"),
        WINTER("winter");

        // 定义实例对应的参数
        private String msg;

        // 必写：通过此构造器给枚举值创建实例
        // 必写：通过此构造器给枚举值创建实例
        // 必写：通过此构造器给枚举值创建实例
        SeasonType2(String msg) {
            this.msg = msg;
        }

        // 通过此方法可以获取到对应实例的参数值
        public String getMsg() {
            return msg;
        }
    }

    //3.实战三有两参
    enum Season {
        // 通过构造函数传递参数并创建实例
        SPRING(1, "spring"),
        SUMMER(2, "summer"),
        AUTUMN(3, "autumn"),
        WINTER(4, "winter");

        // 定义实例对应的参数
        private Integer key;
        private String value;

        // 必写：通过此构造器给枚举值创建实例
        Season(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

        // 很多情况，我们可能从前端拿到的值是枚举类的 key ，然后就可以通过以下静态方法获取到对应枚举值
        public static Season valueofKey(Integer key) {
            for (Season season : Season.values()) {
                if (season.key.equals(key)) {
                    return season;
                }
            }
            throw new IllegalArgumentException("No element matches " + key);
        }

        // 通过此方法可以获取到对应实例的 key 值
        public Integer getKey() {
            return key;
        }

        // 通过此方法可以获取到对应实例的 msg 值
        public String getValue() {
            return value;
        }
    }

    public static void main(String[] args) {
        // 根据实际情况选择下面的用法即可
        SeasonType springType = SeasonType.SPRING;    // 输出 SPRING
        String springString = SeasonType.SPRING.toString();    // 输出 SPRING
        System.out.println(springType);
        System.out.println(springString);
        //总结: 从这里可以看出来，枚举类中声明的每一枚举值其实都调用了构造函数并创建了实例，简单理解就是: 每一个枚举值都是一个实例对象。
        System.out.println(SeasonType.SPRING);

        // 当我们为某个实例类赋值的时候可使用如下方式
        String msg = SeasonType2.SPRING.getMsg();    // 输出 spring
        System.out.println(msg);

        // 输出 key 为 1 的枚举值实例
        Season season = Season.valueofKey(1);
        // 输出 SPRING 实例对应的 key
        Integer key = Season.SPRING.getKey();
        // 输出 SPRING 实例对应的 msg
        String value = Season.SPRING.getValue();
        System.out.println(season);
        System.out.println(key);
        System.out.println(value);

    }
}
