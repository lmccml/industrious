package base;

/**
 * @author lmc
 * @date 2020/1/14 9:55
 */
public class StringTest {
    public static void main(String[] args) {
        String str1 = "what";
        String str2 = str1 + " a nice day"; //new StringBuilder().append(str1).append(" a nice day").toString();
        System.out.println("what a nice day".equals(str2));//true，比较内容
        //因为StringBuilder.toString()是返回return new String(value, 0, count); 所以说 str2 其实是一个 new String，是不在常量池里面的。
        System.out.println("what a nice day" == str2);//false，what a nice day在常量池，str2 是存放在堆中，地址不同


    }
}
