package com.git.hui.boot.scheduler.ano;

import com.git.hui.boot.scheduler.lock.ExecutiveLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by @author yihui in 11:30 20/4/12.
 */
@Aspect
//@Component
public class DemoAop {

    @Around("@annotation(DistributeTask)")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String lock = method.getName();
        DistributeTask task = method.getAnnotation(DistributeTask.class);
        if (ExecutiveLock.instance.tryLock(task.key(), lock)) {
            System.out.println("Aop allow: " + lock);
            return joinPoint.proceed();
        } else {
            System.out.println("Aop ignore : " + lock);
            return null;
        }
    }
}
