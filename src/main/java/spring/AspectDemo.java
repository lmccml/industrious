package spring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author lmc
 * @date 2020/3/11 17:38
 */
@Aspect
@Component
public class AspectDemo {
    @Pointcut("execution(* *.out(..))")
    public void out(){
    }
    @Before("out()")
    public void before(){
        System.out.println("this is a before");
    }
    @Around("out()")
    public Object around(ProceedingJoinPoint p) {
        System.out.println("this is a around");
        Object o = null ;
        try {
            o = p.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
        return o;
    }

    @After("out()")
    public void after(){
        System.out.println("this is a after");
    }
}
