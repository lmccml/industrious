package practice.spring;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

@Data
public class log_event extends ApplicationEvent {
    private String text;
    //source代表事件源
    public log_event(Object source, String text) {
        super(source);
        this.text = text;
    }

}
