package com.git.hui.boot.web.interceptor;

import com.git.hui.boot.web.interceptor.inter.SecurityInterceptor;
import com.git.hui.boot.web.interceptor.server.BasicDemo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yihui
 * @date 21/8/2
 */
@Aspect
@RestController
@SpringBootApplication
public class Application implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public SecurityInterceptor securityInterceptor() {
        return new SecurityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
         registry.addInterceptor(securityInterceptor()).addPathPatterns("/**");
    }

    @Autowired
    private BasicDemo basicDemo;

    @GetMapping(path = "show")
    public String show(String data) {
        return basicDemo.process(data);
    }

    @Around("execution(public * com.git.hui.boot.web.interceptor.server.BasicDemo.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }

}
