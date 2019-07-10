package com.git.hui.boot.aop.factory;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by @author yihui in 09:38 19/7/10.
 */
@Component
public class ProxyDemoAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("before aop [" + method.getName() + "] do sth...................");
    }
}
