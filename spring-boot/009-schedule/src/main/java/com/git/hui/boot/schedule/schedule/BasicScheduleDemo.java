package com.git.hui.boot.schedule.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * Created by @author yihui in 18:17 18/12/10.
 */
@Component
public class BasicScheduleDemo {

    /**
     * 每s执行一次
     *
     * @throws InterruptedException
     */
    @Scheduled(cron = "0/1 * * * * ?")
    private void scheduleAtFixRate() throws InterruptedException {
        long now = System.currentTimeMillis() / 1000;
        Thread.sleep(10);
        System.out.println("FixRate: " + (System.currentTimeMillis() / 1000) + " >>> " + now + " >>> " +
                Thread.currentThread().getName());
    }

    /**
     * 固定两次执行之间延迟2s
     */
    @Scheduled(fixedDelay = 2000)
    private void scheduleDelay() {
        System.out.println("FixDelay: " + (System.currentTimeMillis() / 1000)  + " >>> " +
                Thread.currentThread().getName());
    }

    /**
     * 定时 20:10:00 执行一次
     */
    @Scheduled(cron = "0 10 20 * * ?")
    private void scheduleAtSpecialTime() {
        System.out.println(
                "specialTime: " + (System.currentTimeMillis() / 1000) + " >>> " + Thread.currentThread().getName());
    }

}
