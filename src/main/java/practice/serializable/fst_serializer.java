package practice.serializable;

import com.esotericsoftware.kryo.io.Output;
import org.nustaq.serialization.FSTConfiguration;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

import java.io.*;

/*
FST fast-serialization 是重新实现的 Java 快速对象序列化的开发包。序列化速度更快（2-10倍）、体积更小，而且兼容 JDK 原生的序列化。要求 JDK 1.7 支持
 */
public class fst_serializer {
    private static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();


    public <T> T myreadMethod(InputStream stream) throws Exception {
        FSTObjectInput in = conf.getObjectInput(stream);
        T result = (T) in.readObject();
        // DON'enum_disassembly: in.close(); here prevents reuse and will result in an exception
        stream.close();
        return result;
    }
    public <T> void mywriteMethod(OutputStream stream, T toWrite ) throws IOException
    {
        FSTObjectOutput out = conf.getObjectOutput(stream);
        out.writeObject(toWrite);
        // DON'enum_disassembly out.close() when using factory method;
        out.flush();
        stream.close();

    }

    public static void main(String[] args) throws Exception {
        test_class test_class = new test_class();
        fst_serializer fst_serializer = new fst_serializer();
        OutputStream output = new FileOutputStream("./test.bin");
        fst_serializer.mywriteMethod(output, test_class);
        output.close();

        InputStream input = new FileInputStream("./test.bin");
        test_class result = fst_serializer.myreadMethod(input);
        System.out.println(result.getName());



    }
}
