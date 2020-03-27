package spring;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Configuration
@ComponentScan("spring")
public class main_to_run {
    public static void main(String[] args) {
//        System.out.println("现在开始初始化容器");
//        ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:/applicationContext.xml");
//        System.out.println("容器初始化成功");
//        //得到Preson，并使用
//        Person person = factory.getBean("person",Person.class);
//        System.out.println(person);
//
//        System.out.println("现在开始关闭容器！");
//        ((ClassPathXmlApplicationContext)factory).registerShutdownHook();

        //xml注册bean
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/applicationContext.xml");
        BeanByConstructor beanByConstructor = (BeanByConstructor)ctx.getBean("BeanByConstructor");
        BeanBySetter beanBySetter = (BeanBySetter)ctx.getBean("BeanBySetter");
        System.out.println(beanByConstructor.getId());
        System.out.println(beanBySetter.getName());

        java_bean_first java_bean_first = (java_bean_first)ctx.getBean("java_bean_first");
        System.out.println(java_bean_first.getId());

        //动态注册bean
        BeanDefinitionBuilder bdb = BeanDefinitionBuilder.genericBeanDefinition(java_bean_dynamic.class);
        DefaultListableBeanFactory dbf = (DefaultListableBeanFactory)ctx.getBeanFactory();
        bdb.addPropertyValue("id", 5);
        dbf.registerBeanDefinition("java_bean_dynamic", bdb.getRawBeanDefinition());
        java_bean_dynamic java_bean_dynamic = (spring.java_bean_dynamic)ctx.getBean("java_bean_dynamic");
        System.out.println(java_bean_dynamic.getId());

        //java类和注解配置bean，@ComponentScan自动装配需要通过注册AnnotationConfigApplicationContext获取上下文，xml写的配置则是通过ClassPathXmlApplicationContext加载的
        ApplicationContext context = new AnnotationConfigApplicationContext(main_to_run.class);
        java_bean_annotation java_bean_annotation = (java_bean_annotation)context.getBean("java_bean_annotation");
        System.out.println(java_bean_annotation.getId());

        //创建一个ApplicationEvent对象
        log_event event = new log_event("hello", "This is a test");
        //主动触发该事件
        context.publishEvent(event);
        //这样才会去调用销毁前的执行方法
        ctx.destroy();

    }

    @Bean()
    public java_bean_annotation java_bean_annotation() {
        java_bean_annotation java_bean_annotation = new java_bean_annotation();
        java_bean_annotation.setId(6);
        return java_bean_annotation;
    }


}
