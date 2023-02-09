package net.reduck.jpa.test.aspect;

import net.reduck.jpa.test.vo.UserListTO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author Reduck
 * @since 2022/11/21 09:36
 */
@Aspect
@Component
public class CasAspect {

    @Around(value="execution(* net.reduck.jpa.test.controller.TestController.list(net.reduck.jpa.test.vo.UserListTO))")
    public Object aroundAdvisor(ProceedingJoinPoint point) throws Throwable{

        System.out.println("before method");

        UserListTO to = (UserListTO)point.getArgs()[0];
        to.setEmail("33");
        to.setUsername("22333");

        Object target = point.proceed();

        System.out.println("after method");
        return target;
    }
}
