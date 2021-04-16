package com.git.hui.boot.prometheus.metric;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务。写数据上报
 */
@Component
@EnableScheduling
public class MyJob {

    private Integer count1 = 0;

    private Integer count2 = 0;

    @Autowired
    private MetricContext jobMetrics;

    @Async("main")
    @Scheduled(fixedDelay = 1000)
    public void doSomething() {
        count1++;
        jobMetrics.job1Counter.increment();
        jobMetrics.map.put("x", Double.valueOf(count1));
        System.out.println("task1 count:" + count1);
        if (count1 % 2 == 0) {
            System.out.println("%5==0");
            jobMetrics.map.put("x", Double.valueOf(1));
        }

    }

    @Async
    @Scheduled(fixedDelay = 10000)
    public void doSomethingOther() {
        count2++;
        jobMetrics.job2Counter.increment();
        System.out.println("task2 count:" + count2);
    }
}