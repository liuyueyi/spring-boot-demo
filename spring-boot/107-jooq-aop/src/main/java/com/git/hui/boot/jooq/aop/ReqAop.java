package com.git.hui.boot.jooq.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 10:12 20/9/9.
 */
//@Aspect
@Component
public class ReqAop {

    /**
     * 注意下面这个写法会触发jooq + aop的问题，导致启动非常慢；
     *
     * 作为对比，将类上的 @Aspect 去掉之后就会发现启动得 "飞快" 了
     */
    @Pointcut("execution(* com.git.hui.boot.jooq.service.*.*(..))")
    public void point() {
    }

    @Around("point()")
    public Object req(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            System.out.println("process cost: " + (System.currentTimeMillis() - start));
        }
    }
}
