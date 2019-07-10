package practice.serializable;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
/*
hession	支持	跨语言,序列化后体积小,速度较快
protostuff	支持	跨语言,序列化后体积小,速度快,但是需要Schema,可以动态生成
jackson	支持	跨语言,序列化后体积小,速度较快,且具有不确定性
fastjson	支持	跨语言支持较困难,序列化后体积小,速度较快,只支持java,c#
kryo	支持	跨语言支持较困难,序列化后体积小,速度较快
fst	不支持	跨语言支持较困难,序列化后体积小,速度较快，兼容jdk
 */
public class hessian_serializer {
    /**
     * Hessian实现序列化
     */
    public static <T> byte[] serialize(T t){
        ByteArrayOutputStream byteArrayOutputStream = null;
        HessianOutput hessianOutput = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            // Hessian的序列化输出
            hessianOutput = new HessianOutput(byteArrayOutputStream);
            hessianOutput.writeObject(t);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                byteArrayOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                hessianOutput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Hessian实现反序列化
     */
    public static <T> T deserialize(byte[] bytes) {
        ByteArrayInputStream byteArrayInputStream = null;
        HessianInput hessianInput = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            // Hessian的反序列化读取对象
            hessianInput = new HessianInput(byteArrayInputStream);
            return (T) hessianInput.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                byteArrayInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                hessianInput.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String [] args) {
        test_class test_class = new test_class();
        // 序列化
        byte[] serialize = serialize(test_class);
        System.out.println(serialize);
        // 反序列化
        test_class deserialize = deserialize(serialize);
        System.out.println(deserialize.getName());

    }

}

