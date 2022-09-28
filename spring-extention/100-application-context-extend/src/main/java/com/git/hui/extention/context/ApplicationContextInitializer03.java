package com.git.hui.extention.context;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;

/**
 * @author YiHui
 * @date 2022/9/27
 */
@Order(10)
public class ApplicationContextInitializer03 implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        System.out.println("ApplicationContextInitializer03");
        String env = System.getenv("app.env");
        if ("prod".equalsIgnoreCase(env)) {
            configurableApplicationContext.getEnvironment().setActiveProfiles("prod");
        } else if ("test".equalsIgnoreCase(env)) {
            configurableApplicationContext.getEnvironment().setActiveProfiles("test");
        } else {
            throw new RuntimeException("非法的环境参数：" + env);
        }
    }
}
