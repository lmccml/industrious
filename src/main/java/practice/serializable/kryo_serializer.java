package practice.serializable;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/*
线程不安全，序列化的时候可以用ThreadLocal绑定当前线程的序列化对象
 */
public class kryo_serializer {
    public static void main(String[] args) throws Exception {
        test_class test_class = new test_class();

        //写入
        Kryo kryo = new Kryo();
        Output output = new Output(new FileOutputStream("./test.bin"));
        kryo.writeObject(output, test_class);
        output.close();

        //读取
        Input input = new Input(new FileInputStream("./test.bin"));
        test_class test_class_out = kryo.readObject(input, test_class.class);
        input.close();

        System.out.println(test_class_out.getName());
    }
}
