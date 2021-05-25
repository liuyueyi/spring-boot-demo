package com.git.hui.boot.aop.logaspect;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;

/**
 * Created by @author yihui in 18:00 19/3/13.
 */
@Aspect
@Component
public class LogAspect {
    private static final String SPLIT_SYMBOL = "|";


    @Pointcut("execution(public * com.git.hui.boot.aop.demo.*.*(..)) || @annotation(AnoDot)")
    public void pointcut() {
    }

    @Around(value = "pointcut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object res = null;
        String req = null;
        long start = System.currentTimeMillis();
        try {
            req = buildReqLog(proceedingJoinPoint);
            res = proceedingJoinPoint.proceed();
            return res;
        } catch (Throwable e) {
            res = "Un-Expect-Error";
            throw e;
        } finally {
            long end = System.currentTimeMillis();
            System.out.println(req + "" + JSON.toJSONString(res) + SPLIT_SYMBOL + (end - start));
        }
    }


    private String buildReqLog(ProceedingJoinPoint joinPoint) {
        StopWatch stopWatch = new StopWatch();
        // 目标对象
        stopWatch.start("getTarget");
        Object target = joinPoint.getTarget();
        stopWatch.stop();
        // 执行的方法
        stopWatch.start("getMethod");
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        stopWatch.stop();
        // 请求参数
        stopWatch.start("getArgs");
        Object[] args = joinPoint.getArgs();
        stopWatch.stop();

        stopWatch.start("buildLog");
        StringBuilder builder = new StringBuilder(target.getClass().getName());
        builder.append(SPLIT_SYMBOL).append(method.getName()).append(SPLIT_SYMBOL);
        for (Object arg : args) {
            builder.append(JSON.toJSONString(arg)).append(",");
        }
        String ans = builder.substring(0, builder.length() - 1) + SPLIT_SYMBOL;
        stopWatch.stop();
        System.out.println("LogAspect buildReqLog cost: " + stopWatch.prettyPrint());
        return ans;
    }
}
