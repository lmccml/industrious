package practice.spring;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

@Data
public class LogEvent extends ApplicationEvent {
    private String text;
    //source代表事件源
    public LogEvent(Object source,String text) {
        super(source);
        this.text = text;
    }

}
