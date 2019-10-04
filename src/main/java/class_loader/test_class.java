package class_loader;

public class test_class {
    private int id;
    private String name;
    private int age;

    public test_class() {
        this.id = 1;
        this.name = "test1";
        this.age = 26;
    }

    public void postConstruct(String str) {
        System.out.println("this is a PostConstruct" + str);
    }

    public void preDestroy() {
        System.out.println("omg, im dead!");
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
