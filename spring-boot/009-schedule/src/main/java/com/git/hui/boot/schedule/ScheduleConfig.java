package com.git.hui.boot.schedule;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用自定义的线程池，来替换定时任务的默认线程池
 * Created by @author yihui in 19:59 18/12/10.
 */
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(new ScheduledThreadPoolExecutor(2, new ThreadFactory() {
            private AtomicInteger count = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "demo-" + count.addAndGet(1));
            }
        }));
    }
}
