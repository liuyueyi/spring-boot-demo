package com.git.hui.boot.prometheus.interceptor;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author wuzebang
 * @date 2021/10/26
 */
@Component
public class PrometheusComponent implements ApplicationContextAware {
    private static PrometheusComponent instance;


    /**
     * 请求总数
     */
    private Counter COUNTER;

    /**
     * 正在请求的http数量
     */
    private Gauge IN_PROGRESS_REQUESTS;

    /**
     * 直方图，请求分布情况
     */
    private Histogram REQUEST_LATENCY_HISTOGRAM;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        instance = this;
        CollectorRegistry collectorRegistry = applicationContext.getBean(CollectorRegistry.class);
        // 这里指定SpringBoot容器的CollectorRegistry，如果使用默认的会导致无法收集
        COUNTER = Counter.build().name("demo_rest_req_total").labelNames("path", "method", "code")
                .help("总的请求计数").register(collectorRegistry);
        IN_PROGRESS_REQUESTS = Gauge.build()
                .name("demo_rest_inprogress_req").labelNames("path", "method")
                .help("正在处理的请求数").register(collectorRegistry);
        REQUEST_LATENCY_HISTOGRAM = Histogram.build().labelNames("path", "method", "code")
                .name("demo_rest_requests_latency_seconds_histogram").help("请求耗时分布")
                .register(collectorRegistry);
    }

    public static PrometheusComponent getInstance() {
        return instance;
    }

    public Counter counter() {
        return COUNTER;
    }

    public Gauge gauge() {
        return IN_PROGRESS_REQUESTS;
    }

    public Histogram histogram() {
        return REQUEST_LATENCY_HISTOGRAM;
    }
}
