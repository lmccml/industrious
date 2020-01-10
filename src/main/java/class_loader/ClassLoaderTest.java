package class_loader;

import java.net.URL;

/**
 * @author lmc
 * @date 2020/1/10 14:51
 */
public class ClassLoaderTest {
    public static void main(String[] args) {
        ClassLoader classLoader = ClassTest.class.getClassLoader();
        System.out.println(classLoader);
        System.out.println(classLoader.getParent());
        System.out.println(classLoader.getParent().getParent()); //Bootstrap ， 它是通过C＋＋ 实现的，并不存在于JVM体系内，null

        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        //自定义加路径  -Xbootclasspath/a:/Users/test/src
        for(URL url : urls) {
            System.out.println(url.toExternalForm());
        }


    }
}
