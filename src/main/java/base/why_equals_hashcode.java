package base;

import java.util.Objects;

/*
为什么重写equals一定要重写hashcode
Object类默认的equals比较规则就是比较两个对象的内存地址。而hashcode是本地方法，java的内存是安全的，
因此无法根据散列码得到对象的内存地址，但实际上，hashcode是根据对象的内存地址经哈希算法得来的。
只重写equals而不重写hashcode，那么Student类的hashcode方法就是Object默认的hashcode方法，
由于默认的hashcode方法是根据对象的内存地址经哈希算法得来的，显然此时s1!=s2,故两者的hashcode不一定相等。
然而重写了equals，且s1.equals(s2)返回true，根据hashcode的规则，两个对象相等其哈希值一定相等，
所以矛盾就产生了，因此重写equals一定要重写hashcode，而且从Student类重写后的hashcode方法中可以看出，重写后返回的新的哈希值与Student的两个属性有关。
 */
public class why_equals_hashcode {
    private String name;
    private int age;

    public why_equals_hashcode(String name, int age) {
        this.age = age;
        this.name = name;
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

    @Override
    public int hashCode() {
        //可以自定义写对象的hashcode方法，也可以利用工具类
        return Objects.hash(name, age);
    }

    //自定义的hashcode方法
    public int hashCode2() {
      int name_hash = name == null ? 0 : name.hashCode();
      return name_hash + age;
    }


    /*
    不写的话默认调用的是object的equals方法，其内存地址可能不相等
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        why_equals_hashcode student = (why_equals_hashcode) obj;
        return age == student.age &&
                Objects.equals(name, student.name) &&
                Objects.equals(age, student.age);
    }

    public static void main(String[] args) {
        why_equals_hashcode why_equals_hashcode_one = new why_equals_hashcode("aa", 18);
        why_equals_hashcode why_equals_hashcode_two = new why_equals_hashcode("aa", 18);
        System.out.println(why_equals_hashcode_one.equals(why_equals_hashcode_two));
    }
}
