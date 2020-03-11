package advanced.class_loader;


import java.io.*;


public class custom_loader extends ClassLoader {

    //类加载器名称
    private String name;
    //类加载路径
    private String path;

    /*
   暴露方法自定义加载方法
    */
    public custom_loader(ClassLoader parent, String name, String path) {
        super(parent); //父类加载器
        this.name = name;
        this.path = path;
    }

    /**
     * 重写父类的loadClass方法
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Class<?> clazz = null;
        //判断是不是本类
        if(this.name.equals(name)) {
            //查找是否已经被加载过了
            clazz = this.findLoadedClass(name);
            if(clazz == null) {
                //如果没找到则继续去查找
                clazz = this.findClass(name);
            }
        }
        return super.loadClass(name);
    }

    /**
     * 重写findClass方法，自定义规则
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        //转成二进制字节流，因为JVM只认识二进制不认识字符串
        byte[] b = readFileToByteArray(name);
        return this.defineClass(this.name, b, 0, b.length);
    }

    /**
     * 将包名转换成全路径名，比如
     *
     * temp.a.com.dn.Demo -> D:/temp/a/com/dn/Demo.class
     *
     * @param name
     * @return
     */
    private byte[] readFileToByteArray(String name) {
        InputStream is = null;
        byte[] rtnData = null;
        //转换
        name = name.replaceAll("\\.", "/");
        //拼接
        String filePath = this.path + name + ".class";
        File file = new File(filePath);

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
            is = new FileInputStream(file);
            int tmp = 0;
            while((tmp = is.read()) != -1) {
                os.write(tmp);
            }

            rtnData = os.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return rtnData;
    }

    public Class<?> loadClass() throws ClassNotFoundException {
        return loadClass(this.name);
    }


    /*
    资源隔离 热部署 代码保护
    比较常见的场景就是隔离，比如早些年比较火的Java模块化框架OSGI,把每个Jar包以Bundle的形式运行，每个Bundle有自己的类加载器(不同Bundle可以有相同的类名)，
    Bundle与Bundle之间起到隔离的效果，同时如果一个Bundle依赖了另一个Bundle的某个类，那这个类的加载就委托给导出该类的BundleClassLoader进行加载；
    又比如Tomcat容器，每个WebApp有自己的ClassLoader,加载每个WebApp的ClassPath路径上的类，一旦遇到Tomcat自带的Jar包就委托给CommonClassLoader加载；
    对于公司的一些核心类库，可能会把字节码加密，这样加载类的时候就必须对字节码进行解密，可以通过findClass读取URL中的字节码，然后加密，最后把字节数组交给defineClass()加载。
     */
    public static void main(String[] args) throws Exception {
        //类的全路径名称，不一定必须在项目中，可以在任意位置
        String name = "practice.advanced.class_loader.test_class";
        //类的classes目录
        String path = "/Users/lmc/work_space/industrious/target/classes/";

        test_class test_class = null;

        while(true) {
            //1、实例化自己的类加载器，并将当前线程的类加载器作为父类加载器，并将name和path传进去
            custom_loader loader = new custom_loader(Thread.currentThread().getContextClassLoader(), name, path);
            //2、调用本来的loadClass方法
            Class<?> clazz = loader.loadClass();
            //实例化上面path+name+".class"
            test_class = (test_class)clazz.newInstance();
            //调用方法
            test_class.postConstruct("这是通过自定义类加载器运行的方法");
            //每隔3s执行一次，检查class是否有变化
            Thread.sleep(3000);
        }
    }

}
