package com.git.hui.boot.scheduler.demo;

import com.git.hui.boot.scheduler.ano.DistributeTask;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by @author yihui in 21:09 20/4/10.
 */
@Component
public class ScheduleDemo {

    /**
     * 每s执行一次
     *
     * @throws InterruptedException
     */
    @Scheduled(cron = "0/5 * * * * ?")
    @DistributeTask(key = "tt")
    private void scheduleAtFixRate() throws InterruptedException {
        System.out.println("Rate1: " + LocalDateTime.now() + " >>> " + Thread.currentThread().getName());
    }

    @Scheduled(cron = "0/5 * * * * ?")
    @DistributeTask(key = "tt")
    private void scheduleAtFixRate2() throws InterruptedException {
        System.out.println("Rate2: " + LocalDateTime.now() + " >>> " + Thread.currentThread().getName());
    }
}
