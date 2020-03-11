package advanced.aspect;

import org.springframework.stereotype.Component;

/**
 * @author lmc
 * @date 2020/3/11 17:50
 */
@Component
public class OutTestImpl implements OutTest{
    @Override
    public void out() {
        System.out.println("原始out！");
    }
}
