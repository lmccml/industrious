package genericity;

import java.math.BigDecimal;
import java.util.ArrayList;

public class genericity_test<T> {
    private T id;

    public genericity_test(T id) {
        this.id = id;
    }

    public T get() {
        return id;
    }

    public void set(T id) {
        this.id = id;
    }


    //PECS原则
    ////总结：
    //1. <? extends T> 只能用于方法返回，告诉编译器此返参的类型的最小继承边界为T，T和T的父类都能接收，但是入参类型无法确定，只能接受null的传入
    //2. <? super T>只能用于限定方法入参，告诉编译器入参只能是T或其子类型，而返参只能用Object类接收
    //3. ? 既不能用于入参也不能用于返参
    //最后看一下什么是PECS（Producer Extends Consumer Super）原则，已经很好理解了：
    //频繁往外读取内容的，适合用上界Extends。
    //经常往里插入的，适合用下界Super。
    //
    //List<?>表示可以接受任何类型的集合赋值，赋值就不能随便添加元素了。

    public static void main(String[] args) {

        genericity_test<? extends son> genericity_test = new genericity_test<>(new son());

        //不能存入任何元素
        //genericity_test.set(new father());    //Error  编译错误
        //genericity_test.set(new son());    //Error  编译错误
        genericity_test.set(null);


        //读取出来的东西只能放在Fruit或它的基类里
        father father_one = genericity_test.get();
        Object obj_one = genericity_test.get();
        //son son_one = genericity_test.get();  //Error


        genericity_test<? super father> genericity_test_two = new genericity_test<father>(new father());

        //存入元素正常
        genericity_test_two.set(new father());
        genericity_test_two.set(new son());

        //读取出来的东西只能存放在Object类里
        Object obj_two = genericity_test_two.get();
        //father father_two = genericity_test_two.get();  //Error
        //son son_two = genericity_test_two.get();  //Error

        ArrayList<Object> listObject = new ArrayList<>();
        listObject.add(new BigDecimal("333.33"));

        ArrayList<String> listString = new ArrayList<>();
        listString.add("string test");

        genericity_test.send(listString); //send(ArrayList<? extends String> list)
        //genericity_test.send(listObject);

        genericity_test.send2(listString); //send2(ArrayList<? extends Object> list)
        genericity_test.send2(listObject);

        genericity_test.send3(listString);  //send3(ArrayList<? super String> list)
        genericity_test.send3(listObject);

        //genericity_test.send4(listString); //send4(ArrayList<? super Object> list)
        genericity_test.send4(listObject);

        ArrayList<Object> listRecObject = new ArrayList<>();

        ArrayList<String> listRecString = new ArrayList<>();

        Object object = new Object();

        //listRecObject = genericity_test.receive();  //ArrayList<? super String> receive()
        //listRecString = genericity_test.receive();
        //为了保证一定能够取出来，就必须把其转型成Object对象，但是这个时候就会失去原有对象的类型信息。所以List<? super T>不能够提取数据。
        object = genericity_test.receive();

        //listRecObject = genericity_test.receive();  //ArrayList<? super String> receive2()
        //listRecString = genericity_test.receive();
        object = genericity_test.receive2();

        //listRecObject = genericity_test.receive();  //ArrayList<? extends Object> receive3()
        //listRecString = genericity_test.receive();
        object = genericity_test.receive3();

        //listRecObject = genericity_test.receive();  // ArrayList<? extends Object> receive4()
        //listRecString = genericity_test.receive();
        object = genericity_test.receive4();


    }

    /*
    方法前定义类型和类后的定义互不干扰
    */
//    public <F, D> int mix(F f, D d) {
//        return (int)f + (int)d;
//    }

    //<? extends T> 来保证泛型类型必须是 T 的子类来设定泛型类型的上边界；
    void send(ArrayList<? extends String> list){
        System.out.println(list.get(0));;
    }

    void send2(ArrayList<? extends Object> list){
        System.out.println(list.get(0));;
    }

    //<? super T> 来保证泛型类型必须是T的父类来设定类型的下边界；
    void send3(ArrayList<? super String> list){
        System.out.println(list.get(0));;
    }

    void send4(ArrayList<? super Object> list){
        System.out.println(list.get(0));;
    }

    ArrayList<? super String> receive(){
        return new ArrayList<String>();
    }

    ArrayList<? super String> receive2(){
        return new ArrayList<Object>();
    }

    ArrayList<? extends Object> receive3(){
        return new ArrayList<String>();
    }

    ArrayList<? extends Object> receive4(){
        return new ArrayList<Object>();
    }



}
