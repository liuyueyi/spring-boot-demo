package com.git.hui.boot.spel.aop.aop;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

/**
 * @author yihui
 * @date 21/6/4
 */
@Slf4j
@Aspect
@Component
public class AopAspect implements ApplicationContextAware {
    private ExpressionParser parser = new SpelExpressionParser();
    private ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();

    @Around("@annotation(logAno)")
    public Object around(ProceedingJoinPoint joinPoint, Log logAno) throws Throwable {
        long start = System.currentTimeMillis();
        String key = loadKey(logAno.key(), joinPoint);
        try {
            return joinPoint.proceed();
        } finally {
            log.info("key: {}, args: {}, cost: {}", key,
                    new Gson().toJson(joinPoint.getArgs()),
                    System.currentTimeMillis() - start);
        }
    }

    private String loadKey(String key, ProceedingJoinPoint joinPoint) {
        if (key == null) {
            return key;
        }

        // 使用StandardEvaluationContext，可以使用完整的Spel功能；但是有注入风险
        // 使用new SimpleEvaluationContext.Builder().build(); 提供部分的spel表达式，不支持bean，对象方法调用也会有问题
        StandardEvaluationContext context = new StandardEvaluationContext();

        context.setBeanResolver(new BeanFactoryResolver(applicationContext));
        String[] params = parameterNameDiscoverer.getParameterNames(((MethodSignature) joinPoint.getSignature()).getMethod());
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            context.setVariable(params[i], args[i]);
        }

        return parser.parseExpression(key).getValue(context, String.class);
    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
