package com.git.hui.boot.selfconfig.property;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

/**
 * 注册自定义的配置源
 *
 * @author YiHui
 * @date 2023/6/25
 */
public class SelfConfigContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>,
        EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        System.out.println("postProcessEnvironment");
        initialize(environment);
    }

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        System.out.println("postProcessEnvironment#initialize");
        ConfigurableEnvironment env = configurableApplicationContext.getEnvironment();
        initialize(env);
    }

    protected void initialize(ConfigurableEnvironment environment) {
        if (environment.getPropertySources().contains("selfSource")) {
            // 已经初始化过了，直接忽略
            return;
        }

        MapPropertySource propertySource = new MapPropertySource("selfSource", SelfConfigContext.getInstance().getCache());
        environment.getPropertySources().addFirst(propertySource);
    }
}
