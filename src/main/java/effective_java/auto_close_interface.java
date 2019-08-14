package effective_java;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;

public class auto_close_interface {
    static {
        System.out.println("this is init()");
    }
    public static void main(String[] args) {

        try (AutoCloseableObjecct app = new AutoCloseableObjecct()) {
            System.out.println("--执行main方法--");
            Throwable cc = new Throwable();
            System.out.println(cc);
        } catch (Exception e) {
            System.out.println("--exception--");
        } finally {
            //注意close在finally先执行
            System.out.println("--finally--");
        }

        //JDK1.7之前,释放资源方式
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("/Users/lmc/work_space/industrious/target/classes/application.yml");
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //1.7之后，只要实现了AutoCloseable接口
        try (FileInputStream fileInputStream2 = new FileInputStream("/Users/lmc/work_space/industrious/target/classes/application.yml")) {

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //自己定义类 并实现AutoCloseable
    public static class AutoCloseableObjecct implements AutoCloseable {
        @Override
        public void close() throws Exception {
            System.out.println("--close--");
        }

    }



}
