421-prometheus-metric
---
普罗米修斯监控，实现业务监控，基于AOP来实现自定义埋点上报


### 统计脚本

qps 统计, 状态码统计

```sh
sum(rate(counter_builder_job_counter1_total{job="prometheus-example"}[10s]))
```


rt 统计

```bash
sum(rate(http_server_requests_seconds_sum{uri="/hello"}[10s])) / sum(rate(http_server_requests_seconds_count{uri="/hello"}[10s]))
```