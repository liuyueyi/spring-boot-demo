package com.git.hui.boot.aop.annotation;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * Created by @author yihui in 10:52 19/3/1.
 */
@Aspect
//@Component
public class AnoAspect {

    @Before("execution(public * com.git.hui.boot.aop.demo.*.*(*))")
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("do in Aspect before method called! args: " + JSON.toJSONString(joinPoint.getArgs()));
    }

    @Pointcut("execution(public * com.git.hui.boot.aop.demo.*.*(*))")
    public void point() {
    }

    @After("point()")
    public void doAfter(JoinPoint joinPoint) {
        System.out.println("do in Aspect after method called! args: " + JSON.toJSONString(joinPoint.getArgs()));
    }

    /**
     * 执行完毕之后，通过 args指定参数；通过 returning 指定返回的结果，要求返回值类型匹配
     *
     * @param time
     * @param result
     */
    @AfterReturning(value = "point() && args(time)", returning = "result")
    public void doAfterReturning(long time, String result) {
        System.out.println("do in Aspect after method return! args: " + time + " ans: " + result);
    }

    @Around("point()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("do in Aspect around ------ before");
        Object ans = joinPoint.proceed();
        System.out.println("do in Aspect around ------- over! ans: " + ans);
        return ans;
    }

    @Before("point()")
    public void sameBefore() {
        System.out.println("SameAspect");
    }

    @Before("@annotation(AnoDot)")
    public void anoBefore() {
        System.out.println("AnoAspect");
    }
}
