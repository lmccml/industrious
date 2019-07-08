package practice.spring;

import lombok.Data;
import org.springframework.context.annotation.Lazy;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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

    @PostConstruct
    public void postConstruct() {
        System.out.println("this is a PostConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("omg, im dead!");
    }

}
