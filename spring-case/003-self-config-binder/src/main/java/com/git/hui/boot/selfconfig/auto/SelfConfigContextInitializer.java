package com.git.hui.boot.selfconfig.auto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.HashMap;
import java.util.Map;

/**
 * @author YiHui
 * @date 2023/6/25
 */
public class SelfConfigContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>,
        EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        initialize(environment);
    }

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        ConfigurableEnvironment env = configurableApplicationContext.getEnvironment();
        initialize(env);
    }

    public Map<String, Object> cache = new HashMap<>();

    protected void initialize(ConfigurableEnvironment environment) {
        if (environment.getPropertySources().contains("selfSource")) {
            // 已经初始化过了，直接忽略
            return;
        }

        MutablePropertySources propertySources = new MutablePropertySources(environment.getPropertySources());
        // 将内存的配置信息设置为最高优先级
        cache.put("config.type", 33);
        MapPropertySource propertySource = new MapPropertySource("selfSource", cache);
        propertySources.addFirst(propertySource);
        environment.getPropertySources().addFirst(propertySource);
    }
}
