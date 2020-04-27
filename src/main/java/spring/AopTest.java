package spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author lmc
 * @date 2020/3/11 17:49
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class AopTest {
    @Autowired
    private OutTest outTest;

    @Test
    public void testAopAnnotation() {
        outTest.out();
    }

}
