package book.Java系统性能优化源代码.jsr.helloexamples;

import com.example.PrintElementInfo;

@PrintElementInfo
public class HelloWorld {
    public static final String HELLO = "Hello World!";

    public static void main(String[] args) {
        System.out.println(HELLO);
    }

    public <T> T test(T bean) {
        //具体方法
        return bean;
    }
}
