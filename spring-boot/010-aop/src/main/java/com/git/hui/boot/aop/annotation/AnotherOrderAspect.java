package com.git.hui.boot.aop.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 21:01 19/3/10.
 */
@Aspect
@Order(10)
@Component
public class AnotherOrderAspect {

    @Before("@annotation(AnoDot)")
    public void doBefore() {
        System.out.println("in AnotherOrderAspect before!");
    }

    @After("@annotation(AnoDot)")
    public void doAfter(JoinPoint joinPoint) {
        System.out.println("do AnotherOrderAspect after!");
    }

    @AfterThrowing
    @AfterReturning(value = "@annotation(AnoDot)", returning = "ans")
    public void doAfterReturning(JoinPoint joinPoint, String ans) {
        System.out.println("do AnotherOrderAspect after return: " + ans);
    }

    @Around("@annotation(AnoDot)")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            System.out.println("do AnotherOrderAspect in around before");
            return joinPoint.proceed();
        } finally {
            System.out.println("do AnotherOrderAspect in around over!");
        }
    }
}
