package effective_java;

import java.io.File;
import java.io.FileInputStream;

public class create_obj_unnecessary {
    // Hideously slow! Can you spot the object creation?
    private static long sum() {
        Long sum = 0L;
        for (long i = 0; i <= Integer.MAX_VALUE; i++)
            sum += i;
        return sum;
    }

    // Hideously slow! Can you spot the object creation?
    private static long sum_better() {
        long sum = 0L;
        for (long i = 0; i <= Integer.MAX_VALUE; i++)
            sum += i;
        return sum;
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        System.out.println(create_obj_unnecessary.sum());
        System.out.println(System.currentTimeMillis());

        System.out.println(System.currentTimeMillis());
        System.out.println(create_obj_unnecessary.sum_better());
        System.out.println(System.currentTimeMillis());

    }
}
