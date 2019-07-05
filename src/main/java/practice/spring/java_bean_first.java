package practice.spring;

import lombok.Data;

@Data
public class java_bean_first {
    private int id;
    private String name;
    private int age;
    public java_bean_first() {
        this.id = 1;
        this.name = "test1";
        this.age = 26;
    }
}
