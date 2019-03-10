package com.git.hui.boot.aop.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 嵌套格式的切面使用方式
 * Created by @author yihui in 15:01 19/3/2.
 */
@Aspect
@Component
public class NetAspect {

    @Around("@annotation(AnoDot)")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("In NetAspect doAround before!");
        Object ans = joinPoint.proceed();
        System.out.println("In NetAspect doAround over! ans: " + ans);
        return ans;
    }
}
