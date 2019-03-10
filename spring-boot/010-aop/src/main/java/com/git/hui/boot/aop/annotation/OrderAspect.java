package com.git.hui.boot.aop.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 22:20 19/3/9.
 */
@Order(0)
@Component
@Aspect
public class OrderAspect {

    @Pointcut("execution(public * com.git.hui.boot.aop.order.*.*())")
    public void point() {
    }


    @Order(1)
    @Before(value = "point()")
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("do before!");
    }

    @Order(2)
    @Before("@annotation(AnoDot)")
    public void doAnoBefore(JoinPoint joinPoint) {
        System.out.println("dp AnoBefore");
    }

    @Order(3)
    @Before("@annotation(AnoDot)")
    public void doXBefore(JoinPoint joinPoint) {
        System.out.println("dp XBefore");
    }

    @After(value = "point()")
    public void doAfter(JoinPoint joinPoint) {
        System.out.println("do after!");
    }

    @AfterReturning(value = "point()", returning = "ans")
    public void doAfterReturning(JoinPoint joinPoint, String ans) {
        System.out.println("do after return: " + ans);
    }

    @Around("point()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            System.out.println("do in around before");
            return joinPoint.proceed();
        } finally {
            System.out.println("do in around over!");
        }
    }
}
