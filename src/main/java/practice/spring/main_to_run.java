package practice.spring;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Configuration
@ComponentScan("practice")
public class main_to_run {
    public static void main(String[] args) {
        //xml注册bean
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/applicationContext.xml");
        java_bean_first java_bean_first = (java_bean_first)ctx.getBean("java_bean_first");
        System.out.println(java_bean_first.getId());

        //动态注册bean
        BeanDefinitionBuilder bdb = BeanDefinitionBuilder.genericBeanDefinition(java_bean_dynamic.class);
        DefaultListableBeanFactory dbf = (DefaultListableBeanFactory)ctx.getBeanFactory();
        bdb.addPropertyValue("id", 5);
        dbf.registerBeanDefinition("java_bean_dynamic", bdb.getRawBeanDefinition());
        java_bean_dynamic java_bean_dynamic = (practice.spring.java_bean_dynamic)ctx.getBean("java_bean_dynamic");
        System.out.println(java_bean_dynamic.getId());

        //java类和注解配置bean
        ApplicationContext context = new AnnotationConfigApplicationContext(main_to_run.class);
        java_bean_annotation java_bean_annotation = (java_bean_annotation)context.getBean("java_bean_annotation");
        System.out.println(java_bean_annotation.getId());
    }

    @Bean()
    public java_bean_annotation java_bean_annotation() {
        java_bean_annotation java_bean_annotation = new java_bean_annotation();
        java_bean_annotation.setId(6);
        return java_bean_annotation;
    }


}
