package com.git.hui.boot.schedule.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by @author yihui in 16:00 19/7/10.
 */
@Component
public class CronUsageDemo {

    /**
     * '/' 用来指定增量
     * 'a/b'，表示从a开始，每次增加b
     * 所以表达式 "2 0/1 * * * ?" ==> 表示从每分钟的02s，触发
     * 所以表达式 "2 2/3 * * * ?" ==> 表示从第2分钟开始，每3分钟执行一次，即 xx:2:2（两分2秒）, xx:5:2(五分2秒）
     */
    @Scheduled(cron = "2 0/1 * * * ?")
    private void demo1() {
        System.out.println("now: " + LocalDateTime.now());
    }

    @Scheduled(cron = "2 6 0/1 * * ?")
    private void demo2() {
        System.out.println("222> now: " + LocalDateTime.now());
    }
}
