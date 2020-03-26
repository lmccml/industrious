package advanced.annotation;


import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface custom_annotation_test {
    String value() default "@interface test!" ;
}

//@Retention表示注解信息保留到什么时候，取值只能有一个，类型为RetentionPolicy,它是一一个枚举，有三个取值。
//public enum RetentionPolicy {
//    /**
//     * 注释只在源代码级别保留，编译时被忽略
//     */
//    SOURCE,
//    /**
//     * 注释将被编译器在类文件中记录
//     * 但在运行时不需要JVM保留。这是默认的
//     * 行为.
//     */
//    CLASS,
//    /**
//          *注释将被编译器记录在类文件中
//          *在运行时保留VM，因此可以反读。
//     * @see java.lang.reflect.AnnotatedElement
//     */
//    RUNTIME
//}

//如果没有声明@ Target,默认为适用于所有类型。
//public enum ElementType {
//    /**
//     * 类, 接口 (包括注释类型), 或 枚举 声明
//     */
//    TYPE,
//
//    /**
//     * 字段声明（包括枚举常量）
//     */
//    FIELD,
//
//    /**
//     * 方法声明(Method declaration)
//     */
//    METHOD,
//
//    /**
//     * 正式的参数声明
//     */
//    PARAMETER,
//
//    /**
//     * 构造函数声明
//     */
//    CONSTRUCTOR,
//
//    /**
//     * 局部变量声明
//     */
//    LOCAL_VARIABLE,
//
//    /**
//     * 注释类型声明
//     */
//    ANNOTATION_TYPE,
//
//    /**
//     * 包声明
//     */
//    PACKAGE,
//
//    /**
//     * 类型参数声明
//     *
//     * @since 1.8
//     */
//    TYPE_PARAMETER,
//
//    /**
//     * 使用的类型
//     *
//     * @since 1.8
//     */
//    TYPE_USE
//}

//@Documented描述-javadoc
//@Documented用于描述其它类型的annotation应该被作为被标注的程序成员的公共API，因此可以被例如javadoc此类的工具文档化。
//

//@Inherited阐述了某个被标注的类型是被继承的
//@Inherited 元注解是一个标记注解，
//@Inherited阐述了某个被标注的类型是被继承的。
//如果一个使用了@Inherited修饰的annotation类型被用于一个class，则这个annotation将被用于该class的子类。