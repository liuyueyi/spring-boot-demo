package com.git.hui.demo.prometheus.metric;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author yihui
 * @date 2021/12/21
 */
@Aspect
@Component
public class MetricAop {
    @Autowired
    private MeterRegistry meterRegistry;

    @Pointcut("execution(public * com.git.hui.demo.prometheus.service.*.*(..))")
    public void point() {
    }

    /**
     * 拦截Service共有方法，上报接口执行情况到Prometheus
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("point()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        String service = joinPoint.getTarget().getClass().getSimpleName();
        String method = joinPoint.getSignature().getName();

        Timer.Sample sample = Timer.start();
        boolean hasError = false;
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            hasError = true;
            throw e;
        } finally {
            Timer timer = Timer.builder("micro_service_histogram")
                    .minimumExpectedValue(Duration.ofMillis(1))
                    .maximumExpectedValue(Duration.ofMinutes(3))
                    .sla(Duration.ofMillis(10), Duration.ofMillis(50), Duration.ofMillis(100), Duration.ofMillis(300), Duration.ofMillis(1000))
                    .tags(Tags.of("service", service, "method", method, "err", String.valueOf(hasError)))
                    .register(meterRegistry);
            sample.stop(timer);
        }
    }
}
