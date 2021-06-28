package com.git.hui.boot.redis.list;

import com.git.hui.boot.redis.list.component.Consumer;
import com.git.hui.boot.redis.list.component.RedisDelayListWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * @author yihui
 * @date 2021/5/7
 */
@EnableScheduling
@RestController
@SpringBootApplication
public class Application {
    private static final String TEST_DELAY_QUEUE = "test";
    private static final String DEMO_DELAY_QUEUE = "demo";
    @Autowired
    private RedisDelayListWrapper redisDelayListWrapper;

    private Random random = new Random();

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @GetMapping(path = "publish")
    public String publish(String msg, Long delayTime) {
        if (delayTime == null) {
            delayTime = 10_000L;
        }

        String queue = random.nextBoolean() ? TEST_DELAY_QUEUE : DEMO_DELAY_QUEUE;
        msg = queue + "#" + msg + "#" + (System.currentTimeMillis() + delayTime);
        redisDelayListWrapper.publish(queue, msg, delayTime);
        System.out.println("延时: " + delayTime + "ms后消费: " + msg + " now:" + LocalDateTime.now());
        return "success!";
    }


    @Consumer(topic = TEST_DELAY_QUEUE)
    public void consumer(RedisDelayListWrapper.DelayMsg delayMsg) {
        System.out.println("TEST消费延时消息: " + delayMsg + " at:" + System.currentTimeMillis());
    }

    @Consumer(topic = DEMO_DELAY_QUEUE)
    public void consumerDemo(RedisDelayListWrapper.DelayMsg delayMsg) {
        System.out.println("DEMO消费延时消息: " + delayMsg + " at:" + System.currentTimeMillis());
    }
}
