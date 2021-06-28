package com.git.hui.boot.redis.list.component;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author yihui
 * @date 2021/5/7
 */
@Aspect
@Component
public class ConsumerAspect {

    @Around("@annotation(consumer)")
    public Object around(ProceedingJoinPoint joinPoint, Consumer consumer) throws Throwable {
        Object[] args = joinPoint.getArgs();
        boolean check = false;
        for (Object obj : args) {
            if (obj instanceof RedisDelayListWrapper.DelayMsg) {
                check = consumer.topic().equals(((RedisDelayListWrapper.DelayMsg) obj).getTopic());
            }
        }

        if (!check) {
            // 不满足条件，直接忽略
            return null;
        }

        // topic匹配成功，执行
        return joinPoint.proceed();
    }

}
