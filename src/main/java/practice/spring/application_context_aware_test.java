package practice.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/*
在我们的web程序中，用spring来管理各个实例(bean), 有时在程序中为了使用已被实例化的bean, 通常会用到这样的代码：
ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext-common.xml");
AbcService abcService = (AbcService)appContext.getBean("abcService");
但是这样就会存在一个问题：因为它会重新装载applicationContext-common.xml并实例化上下文bean，
如果有些线程配置类也是在这个配置文件中，那么会造成做相同工作的的线程会被启两次。
一次是web容器初始化时启动，另一次是上述代码显示的实例化了一次。当于重新初始化一遍！！！！这样就产生了冗余。
 */
public class application_context_aware_test implements ApplicationContextAware {
    /*
    不用类似new ClassPathXmlApplicationContext()的方式，从已有的spring上下文取得已实例化的bean。通过ApplicationContextAware接口进行实现。
    当一个类实现了这个接口（ApplicationContextAware）之后，
    这个类就可以方便获得ApplicationContext中的所有bean。
    换句话说，就是这个类可以直接获取spring配置文件中，所有有引用到的bean对象。
    */
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        // TODO Auto-generated method stub
        context = applicationContext;
    }
    //获得applicationContext
    public static ApplicationContext getApplicationContext() {
        //assertContextInjected();
        return context;
    }
    public static void clearHolder(){
        context=null;
    }
    //获取Bean
    public static <T> T getBean(Class<T> requiredType){
        //assertContextInjected();
        return (T) getApplicationContext().getBean(requiredType);
    }
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name){
        assertContextInjected();
        return (T) getApplicationContext().getBean(name);
    }
    //判断application是否为空
    public static void assertContextInjected(){
        if (context == null ){
            System.out.println("application未注入 ，请在springContext.xml中注入SpringHolder!");
        }
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/applicationContext.xml");
        application_context_aware_test.getBean("java_bean_first");
    }
}
