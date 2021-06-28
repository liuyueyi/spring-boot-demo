package com.git.hui.demo.prometheus;

import com.git.hui.demo.prometheus.metric.MetricWrapper;
import com.git.hui.demo.prometheus.service.DemoService;
import com.git.hui.demo.prometheus.service.HelloService;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author yihui
 * @date 2021/4/19
 */
@EnableScheduling
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    MeterRegistryCustomizer<MeterRegistry> configurer(@Value("${spring.application.name}") String applicationName) {
        return (registry) -> {
            registry.config().commonTags("application", applicationName);
            MetricWrapper.setMeterRegistry(registry);
        };
    }

    @Autowired
    private DemoService demoService;
    @Autowired
    private HelloService helloService;
    private Random random = new Random();

    private void call(Runnable runnable, CountDownLatch latch) {
        new Thread(() -> {
            try {
                runnable.run();
            } finally {
                latch.countDown();
            }
        }).start();
    }

    @Async("main")
    @Scheduled(fixedDelay = 100)
    public void doDemoCall() {
        CountDownLatch latch = new CountDownLatch(3);
        call(() -> demoService.add(random.nextInt(10), random.nextInt(30)), latch);
        call(() -> demoService.sub(random.nextInt(10), random.nextInt(30)), latch);
        call(() -> demoService.divide(random.nextInt(10), random.nextInt(30)), latch);
        latch.countDown();
    }

    @Async
    @Scheduled(fixedDelay = 100)
    public void doHelloCall() {
        CountDownLatch latch = new CountDownLatch(2);
        call(() -> helloService.hello("YiHui " + random.nextInt(30)), latch);
        call(() -> helloService.welcome("YiHui " + random.nextInt(30)), latch);
        latch.countDown();
    }
}
