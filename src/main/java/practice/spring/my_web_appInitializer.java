package practice.spring;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/*
    WebApplicationInitializer
    传统上，我们基于web.xml这种方式来配置web应用，
    而WebApplicationInitializer的实现支持在servlet 3.0以上的环境里通过编程的方式来配置ServletContext，这种方式既可以替换web.xml这种方式，也可以和web.xml这种方式共存。
    这种SPI的实现通常由SpringServletContainerInitializer来自动发现，SpringServletContainerInitializer可以被servlet 3.0以上的容器自动启动。
    绝大多数spring开发者构建web应用时需要注册spring DispatcherServlet到WEB/web.xml如下方式：
   <servlet>
    <servlet-name>dispatcher</servlet-name>
    <servlet-class>
      org.springframework.web.servlet.DispatcherServlet
    </servlet-class>
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>/WEB-INF/spring/dispatcher-config.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>dispatcher</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>

 */
public class my_web_appInitializer implements WebApplicationInitializer {
    public void onStartup(ServletContext servletContext) throws ServletException {
        /*
        servlet3.0的ServletContext#addServlet方法，我们注册一个DispatcherServlet的实例，
        这意味着DispatcherServlet可以如其他Object一样，接受在应用上下文中通过构造器进行注入。
        这种方式不但简单还很明了。不需要关注init-param的处理等等，仅有通常的javaBean样式的属性和构造参数。
        可以在DispatcherServlet注入之前，尽可能的自由的创建spring context、使用spring context。
        绝大部分spring web组件已经更新来支持这种注册方式，
        你会发现DispatcherServlet、FrameworkServlet、ContextLoaderListener和DelegatingFilterProxy现在都支持构造参数。
        尽管个别组件(非spring的，其他第三方的)没有更新到支持WebApplicationInitializer的使用，它们仍然可以使用。
        servlet 3.0的ServletContext api支持编码式设置init-params,context-param等的属性。
        基于编码式的配置,WEB/web.xml可以通过WebApplicationInitializer样式的代码完全替换掉，
        但真正的dispatcher-config.xml spring配置文件仍然是基于xml方式的。WebApplicationInitializer也是一个很棒的方式来进行spring的基于编程方式的配置类，
        详情参考org.springframework.context.annotation.Configuration。
         */
        XmlWebApplicationContext appContext = new XmlWebApplicationContext();
        appContext.setConfigLocation("/WEB-INF/spring/dispatcher-config.xml");
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(appContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }


    /*
    WEB/web.xml和WebApplicationInitializer不是相斥的。例如web.xml可以注册一个servlet，WebApplicationInitializer可以注册另外一个servlet。
    一个WebApplicationInitializer甚至可以通过方法如ServletContext#getServletRegistration(String)来修改web.xml中的注册信息。
    然而，若应用中出现web.xml，它的version属性必须设置成3.0或者以上，否则ServletContainerInitializer将会在servlet容器启动时被忽略启动。
    tomcat下映射到"/"
    apache tomcat映射它内部的DefaultServlet到"/"，并且当tomcat版本小于7.0.14时，这个映射属性不能通过编码重写。
    7.0.15解决了这个问题。重写"/"映射已经在glassFish3.1中进行了验证确认。
    用户自定义的@Configuration配置类AppConfig和DispatcherConfig来替换spring的xml文件来重构上面的例子。
    这个示例也有一些超出部分，用来展示root application context的的通用配置和ContextLoaderListener的注册：
     */
    public void onStartup2(ServletContext servletContext) {
        // Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext rootContext =
                new AnnotationConfigWebApplicationContext();
        //rootContext.register(AppConfig.class);

        // Manage the lifecycle of the root application context
        servletContext.addListener(new ContextLoaderListener(rootContext));

        // Create the dispatcher servlet's Spring application context
        AnnotationConfigWebApplicationContext dispatcherContext =
                new AnnotationConfigWebApplicationContext();
        //dispatcherContext.register(DispatcherConfig.class);

        // Register and map the dispatcher servlet
        ServletRegistration.Dynamic dispatcher =
                servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }

}
