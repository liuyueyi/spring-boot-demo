package com.git.hui.boot.prometheus.interceptor;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;

/**
 * @author yihui
 * @date 2021/11/15
 */
public class MetricInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private MeterRegistry meterRegistry;
    private ThreadLocal<Timer.Sample> threadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 总计数 + 1
        meterRegistry.counter("micro_req_total", Tags.of("url", request.getRequestURI(), "method", request.getMethod())).increment();
        // 处理中计数 +1
        meterRegistry.gauge("micro_process_req", Tags.of("url", request.getRequestURI(), "method", request.getMethod()), 1);

        Timer.Sample sample = Timer.start();
        threadLocal.set(sample);
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        try {
            super.postHandle(request, response, handler, modelAndView);
        } finally {
            meterRegistry.gauge("micro_process_req", Tags.of("url", request.getRequestURI(), "method", request.getMethod()), -1);
            //  Timer timer = meterRegistry.timer("micro_req_histogram", Tags.of("url", request.getRequestURI(), "method", request.getMethod(), "code", String.valueOf(response.getStatus())));
            Timer timer = Timer.builder("micro_req_histogram").minimumExpectedValue(Duration.ofMillis(1)).maximumExpectedValue(Duration.ofMinutes(3))
                    .sla(Duration.ofMillis(10), Duration.ofMillis(50), Duration.ofMillis(100), Duration.ofMillis(300), Duration.ofMillis(1000))
                    .tags(Tags.of("url", request.getRequestURI(), "method", request.getMethod(), "code", String.valueOf(response.getStatus())))
                    .register(meterRegistry);
            threadLocal.get().stop(timer);
            threadLocal.remove();
        }
    }
}
