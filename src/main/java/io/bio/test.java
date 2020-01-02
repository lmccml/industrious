package io.bio;

import java.io.File;

/**
 * @author lmc
 * @date 2020/1/2
 */
public class test {
    public static void main(String[] args) {
        String project_path = System.getProperty("user.dir");
        String class_path = test.class.getResource("").getPath();
        System.out.println(class_path);

        //File file = new File()
    }
}
