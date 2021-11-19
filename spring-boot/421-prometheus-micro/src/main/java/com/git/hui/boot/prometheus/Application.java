package com.git.hui.boot.prometheus;

import com.git.hui.boot.prometheus.interceptor.MetricInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Random;

/**
 * @author yihui
 * @date 2021/4/16
 */
@RestController
@SpringBootApplication
public class Application implements WebMvcConfigurer {
    private Random random = new Random();

    @Bean
    public MetricInterceptor metricInterceptor() {
        return new MetricInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(metricInterceptor()).addPathPatterns("/**");
    }

    @GetMapping(path = "hello")
    public String hello(String name) {
        int sleep = random.nextInt(200);
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello sleep: " + sleep + " for " + name;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
