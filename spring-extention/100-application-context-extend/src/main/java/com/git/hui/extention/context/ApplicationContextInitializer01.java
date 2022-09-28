package com.git.hui.extention.context;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;

/**
 * @author YiHui
 * @date 2022/9/27
 */
@Order(5)
public class ApplicationContextInitializer01 implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        System.out.println("ApplicationContextInitializer01");
    }
}
