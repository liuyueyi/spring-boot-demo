package com.git.hui.boot.multi.datasource.dynamic.ano;

import com.git.hui.boot.multi.datasource.dynamic.DSTypeContainer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author yihui
 * @date 21/1/9
 */
@Aspect
@Component
public class DsAspect {

    @Around("@within(DS)")
    public Object dsAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        DS ds = (DS) proceedingJoinPoint.getSignature().getDeclaringType().getAnnotation(DS.class);
        try {
            DSTypeContainer.setDataBaseType(ds == null ? null : ds.value());
            return proceedingJoinPoint.proceed();
        } finally {
            DSTypeContainer.clearDataBaseType();
        }
    }
}
