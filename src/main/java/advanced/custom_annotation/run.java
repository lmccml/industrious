package advanced.custom_annotation;

import java.lang.annotation.Annotation;

/**
 * @author lmc
 * @date 2020/3/11 15:41
 */
@custom_annotation_test
public class run {
    public static void main(String[] args) {
        Class cls = run.class;
        Annotation[] annotations = cls.getAnnotations();
        custom_annotation_test custom_annotation_test = (custom_annotation_test)annotations[0];
        System.out.println(custom_annotation_test.value());
    }
}
