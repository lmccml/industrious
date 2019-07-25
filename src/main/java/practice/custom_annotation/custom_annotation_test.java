package practice.custom_annotation;


import org.nustaq.serialization.annotations.Conditional;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface custom_annotation_test {
    String value() ;
}
