package practice.spring;

import org.springframework.web.SpringServletContainerInitializer;

public class spring_servlet_containerInitializer {
    public static void main(String[] args) {
        /*
        和传统基于web.xml的方式不同，servlet 3.0 ServletContainerInitializer 使用spring的WebApplicationInitializer来支持对servlet container的基于编程的配置支持。
        工作机制:
        假定spring-web模块的jar都出现在classpath上，在容器启动时，servlet 3.0兼容的容器将会加载类，并初始化，然后触发它的onStartup方法。
        Jar服务Api 方法ServiceLoader#load(class)发现spring-web模块的META-INF/services/javax.servlet.ServletContainerInitializer服务提供配置文件，
        详情参考http://docs.oracle.com/javase/6/docs/technotes/guides/jar/jar.html#Service%20Provider
        和web.xml共用:
        一个web应用在启动阶段选择限制classpath 扫描的servlet container的数量的方式有两种，一种通过web.xml的属性metadata-complete，它控制server注解的扫描。
        另一种是通过web.xml中的absolute-ordering属性，它控制哪些web片段(例如jar文件)允许servletContainerInitializer扫描。
        当使用这些特色，springServletContainerInitializer通过增加spring_web到web.xml的命名片段中来启用这些，如下所示：
          <absolute-ordering>
            <name>some_web_fragment</name>
            <name>spring_web</name>
          </absolute-ordering>
        与spring的WebApplicationInitializer的关系:
        spring的WebApplicationInitializer spi仅仅包含了一个方法WebApplicationInitializer#onStartup(ServletContext)，
        类似于ServletContainerInitializer#onStartup(Set, ServletContext)。
        SpringServletContainerInitializer负责初始化并对用户定义的WebApplicationInitializer代理servletContext。
        然后负责让每个WebApplicationInitializer去做servletContext初始化的具体工作。代理的精确过程描述在下文的onStartup方法。
         */
        SpringServletContainerInitializer springServletContainerInitializer = new SpringServletContainerInitializer();
    }
}
