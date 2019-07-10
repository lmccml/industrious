package practice.serializable;

import java.io.*;


//https://www.cnblogs.com/chy18883701161/p/10928229.html
public class custom_serializable implements Serializable {
    private int id;
    private String name;
    private String password;
    //......其他成员变量

    public custom_serializable(int id,String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    //自定义序列化
    private void writeObject(ObjectOutputStream out) throws IOException {
        //只序列化以下3个成员变量
        out.writeInt(id);
        out.writeObject(name);
        //写入反序后的密码，当然我们也可以使用其他加密方式。这样别人打开文件，看到的就不是真正的密码，更安全。
        out.writeObject(new StringBuffer(password).reverse());
    }

    //自定义反序列化。注意：read()的顺序要和write()的顺序一致。比如说序列化时写的顺序是id、name、password，反序列化时读的顺序也要是id、name、password。
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        this.id = in.readInt();
        //readObject()返回的是Object，要强制类型转换
        this.name = (String) in.readObject();
        //反序才得到真正的密码
        StringBuffer pwd = (StringBuffer) in.readObject();
        this.password = pwd.reverse().toString();
    }

    public static void main(String[] args) throws Exception {
        custom_serializable custom_serializable = new custom_serializable(1,"张三","1234");

        //序列化
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("./obj.txt"));
        //调用我们自定义的writeObject()方法
        out.writeObject(custom_serializable);
        out.close();

        //反序列化
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("./obj.txt"));
        //调用自定义的readObject()方法
        custom_serializable user = (custom_serializable) in.readObject();   //写掉了一句   in.close()

        //测试
        System.out.println(custom_serializable.getId());   //1
        System.out.println(user.getName());   //张三
        System.out.println(user.getPassword());   //1234
    }
}