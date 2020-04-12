package com.git.hui.boot.scheduler.config;

import org.springframework.aop.support.AopUtils;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.util.Assert;

import java.lang.reflect.Method;

/**
 * Created by @author yihui in 20:10 20/4/10.
 */
public class MyScheduledAnnotationBeanPostProcessor extends ScheduledAnnotationBeanPostProcessor {
    @Override
    protected Runnable createRunnable(Object target, Method method) {
        Assert.isTrue(method.getParameterCount() == 0, "Only no-arg methods may be annotated with @Scheduled");
        Method invocableMethod = AopUtils.selectInvocableMethod(method, target.getClass());
        return new MyScheduledMethodRunnable(target, invocableMethod);
    }
}
