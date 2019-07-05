package practice.spring;

import lombok.Data;

@Data
public class java_bean_second {
    private int id;
    private String name;
    private int age;
    public java_bean_second() {
        this.id = 2;
        this.name = "test2";
        this.age = 3;
    }
}
