package com.git.hui.druid;

import com.git.hui.druid.demo.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author YiHui
 * @date 2022/10/9
 */
@EnableScheduling
@SpringBootApplication
public class Application {
    @Autowired
    private ScheduleService scheduleService;

    @Bean("mySchedule")
    public ScheduledThreadPoolExecutor mySchedule() {
        return new ScheduledThreadPoolExecutor(4);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Async
    @Scheduled(fixedRate =1_000)
    public void schedule() {
        try {
            scheduleService.queryAndPrint();
        } catch (Exception e) {
            System.out.println(LocalDateTime.now() + ":" + e.getMessage().replaceAll("\n", "->"));
        }
    }
}
