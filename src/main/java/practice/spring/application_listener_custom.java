package practice.spring;

/*
ApplicationContext事件机制是观察者设计模式的实现，通过ApplicationEvent类和ApplicationListener接口，可以实现ApplicationContext事件处理。

如果容器中有一个ApplicationListener Bean，每当ApplicationContext发布ApplicationEvent时，ApplicationListener Bean将自动被触发。这种事件机制都必须需要程序显示的触发。

其中spring有一些内置的事件，当完成某种操作时会发出某些事件动作。比如监听ContextRefreshedEvent事件，当所有的bean都初始化完成并被成功装载后会触发该事件

实现ApplicationListener<ContextRefreshedEvent>接口可以收到监听动作，然后可以写自己的逻辑。

同样事件可以自定义、监听也可以自定义，完全根据自己的业务逻辑来处理。
 */

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

//不管是内置监听还是外部自定义监听一定要把实现ApplicationListener的类定义成一个bean才行，可以是通过注解@Component等也可以通过xml的方式去执行。
@Component
public class application_listener_custom implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof log_event) {
            log_event logevent =  (log_event)applicationEvent;
            System.out.println("日志内容：" + logevent.getText());
            System.out.println("日志时间戳：" + logevent.getTimestamp());
        }

    }
}
