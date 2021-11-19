420-prometheus-basic
---
普罗米修斯监控

默认的端点: `/actuator/prometheus`

### 统计脚本

qps 统计, 状态码统计

```sh
sum(rate(counter_builder_job_counter1_total{job="prometheus-example"}[10s]))
```


rt 统计

```bash
sum(rate(http_server_requests_seconds_sum{uri="/hello"}[10s])) / sum(rate(http_server_requests_seconds_count{uri="/hello"}[10s]))
```

### 自定义埋点上报

这里直接使用的是 `simple-client` 包，相比较于`micrometer`相对麻烦一点，好处就是控制性更强一点

```java
/**
 * 请求总数
 */
private Counter reqCounter;

/**
 * 正在请求的http数量
 */
private Gauge duringReqGauge;

/**
 * 直方图，请求分布情况
 */
private Histogram reqLatencyHistogram;

@Override
public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    instance = this;
    CollectorRegistry collectorRegistry = applicationContext.getBean(CollectorRegistry.class);
    // 这里指定SpringBoot容器的CollectorRegistry，如果使用默认的会导致无法收集
    reqCounter = Counter.build().name("demo_rest_req_total").labelNames("path", "method", "code")
            .help("总的请求计数").register(collectorRegistry);
    duringReqGauge = Gauge.build()
            .name("demo_rest_inprogress_req").labelNames("path", "method")
            .help("正在处理的请求数").register(collectorRegistry);
    reqLatencyHistogram = Histogram.build().labelNames("path", "method", "code")
            .name("demo_rest_requests_latency_seconds_histogram").help("请求耗时分布")
            .register(collectorRegistry);
}
```

重点注意上面的三个metric在初始化时，通过register来指定`CollectorRegistry`，这样SpringBoot的采集端点就可以收集到我们自定义上报的数据；否则这些数据不会被采集

Prometheus主要有四个metric，虽然这里只介绍了三个(Histogram与Summary用法差不多，没有额外加上)

- Counter: 只增不减，可用于统计总的请求数之类的业务指标
- Gauge: 可增可减，用于实时指标的收集比较合适，如当前在处理的请求数，线程数，内存运行情况
- Histogram: 直方图，如用于统计请求耗时分布情况
