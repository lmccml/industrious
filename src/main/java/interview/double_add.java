package interview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class double_add {
    public static void main(String[] args) {
        int i = 0;
        int j = 0;
        int left = ++i;//先加再赋值
        int right = j++;//先赋值再加
        int mul_left = ++i * 5;//
        int mul_right = j++ * 5;
        System.out.println(i);//1
        System.out.println(j);//1
        System.out.println(left);//1
        System.out.println(right);//0
        System.out.println(mul_left);//10
        System.out.println(mul_right);//5
    }
}
