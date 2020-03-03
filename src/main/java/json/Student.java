package json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;

@Data
public class Student {
    /*JackSon的常用注解
    @JsonInclude(Include.NON_EMPTY)
    仅在属性不为空时序列化此字段，对于字符串，即null或空字符串
    @JsonIgnore
    序列化时忽略此字段
    @JsonProperty(value = “user_name”)
    指定序列化时的字段名，默认使用属性名
    */

    private int id;
    private String name;
    private String sex;
    private ArrayList<String> array;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", array=" + Arrays.toString(array.toArray()) +
                '}';
    }
}
