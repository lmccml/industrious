package base;

import java.sql.SQLOutput;

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

        //--加入常量池的方式--
        //JDK1.7以上当常量池中不存在"1"这个字符串的引用，在堆内存中new一个新的String对象，将这个对象的引用加入常量池。（跟1.6的区别是常量池不再存放对象，只存放引用。）
        //直接使用双引号声明出来的String 对象会直接存储在常量池中。
        //如果不是用双引号声明的String 对象，可以使用String 提供的intern方法。intern 方法会从字符串常量池中查询当前字符串是否存在，若不存在就会将当前字符串放入常量池中。

        //单纯的在堆内存中new一个String对象
        String s1 = new String("1"); //s1对象
        //当常量池中不存在"1"这个字符串的引用，将这个对象的引用加入常量池，返回这个对象的引用。
        //当常量池中存在"1"这个字符串的引用，返回这个对象的引用；
        s1 = s1.intern();  //s1返回常量池对象的引用
        s1.intern(); //s1还是s1
        String s2 = "1";
        System.out.println(s1 == s2);//true

        String s3 = new String("1") + new String("1"); //s3对象引用 + new String创建的对象
        s3.intern(); //s3常量池引用对象指向了s3对象的引用，，就是为了在常量池加入“11”
        String s4 = "11"; //s4指向了常量池s3的引用，即指向了s3对象的引用
        System.out.println(s3 == s4);//true

        //（1）现在当有人问 String str = new String(“abc”);创建了几个对象，常量池有abc字段是1个，常量池没有"abc"字段则是2个。
        //（2）String str=“abc”;创建了几个对象（如果常量池里面已经有对象了就是0个。如果没有就是1个）;
        //（3）new String(“abc”).intern();创建了几个对象（如果常量池里面已经有该字符串对象了就是1个，如果没有就是两个）



    }
}
