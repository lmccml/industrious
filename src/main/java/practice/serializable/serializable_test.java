package practice.serializable;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
/*
一个对象序列化的接口，一个类只有实现了Serializable接口，它的对象才能被序列化。
序列化:
是将对象状态转换为可保持或传输的格式的过程。与序列化相对的是反序列化，它将流转换为对象。这两个过程结合起来，可以轻松地存储和传输数据。
把对象转换为字节序列的过程称为对象的序列化(指堆内存中的java对象数据，通过某种方式把对存储到磁盘文件中，或者传递给其他网络节点（网络传输）。
这个过程称为序列化，通常是指将数据结构或对象转化成二进制的过程。)
反序列化:
把字节序列恢复为对象的过程称
 */
public class serializable_test implements Serializable {
    /*
    从说明中我们可以看到，如果我们没有自己声明一个serialVersionUID变量,接口会默认生成一个serialVersionUID
    但是强烈建议用户自定义一个serialVersionUID,因为默认的serialVersinUID对于class的细节非常敏感，
    反序列化时可能会导致InvalidClassException这个异常
    */
    private static final long serialVersionUID = 1024L;
    //?????serialVersionUID要不要指定呢？///
    /*
    这个serialVersionUID是用来辅助对象的序列化与反序列化的，
    原则上序列化后的数据当中的serialVersionUID与当前类当中的serialVersionUID一致，
    那么该对象才能被反序列化成功。
    这个serialVersionUID的详细的工作机制是：
    在序列化的时候系统将serialVersionUID写入到序列化的文件中去，
    当反序列化的时候系统会先去检测文件中的serialVersionUID是否跟当前的文件的serialVersionUID是否一致，
    如果一致则反序列化成功，否则就说明当前类跟序列化后的类发生了变化，
    比如是成员变量的数量或者是类型发生了变化，那么在反序列化时就会发生crash，并且回报出错误
     */
    //只有同一次编译生成的class才会生成相同的serialVersionUID
    //但是如果出现需求变动，Bean类发生改变，则会导致反序列化失败。为了不出现这类的问题，所以我们最好还是显式的指定一个serialVersionUID。


    //静态变量不会被序列化（ static,transient）
    //当一个父类实现序列化，子类自动实现序列化，不需要显式实现Serializable接口。
    //当一个对象的实例变量引用其他对象，序列化该对象时也把引用对象进行序列化。

    /*
    JVM会忽略transient变量的原始值并将默认值保存到文件中。因此，transient意味着不要序列化。
     */
    transient int transient_variable = 2;

    /*
    静态变量不是对象状态的一部分，因此它不参与序列化。所以将静态变量声明为transient变量是没有用处的。
     */
    static String static_variable = "ss";

    /*
    自定义序列化的类需要继承Serializable接口，并要写三个方法：
    */

    // //序列化时会调用此方法
    private  void  writeObject(ObjectOutputStream out) {

    }

    //反序列化时会调用此方法
    private  void  readObject(ObjectInputStream in) {

    }

    //当序列化的类版本和反序列化的类版本不同时，或者ObjectInputStream流被修改时，会调用此方法。
    private  void  readObjectNoData() {

    }

    //Serializable接口中没有任何成员，上面3个方法都是我们自己写的。
    //三个方法都是可选的，不强制，但一般都要实现前2个方法。

}
