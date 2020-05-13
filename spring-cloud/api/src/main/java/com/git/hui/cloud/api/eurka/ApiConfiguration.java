package com.git.hui.cloud.api.eurka;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by @author yihui in 16:13 20/5/12.
 */
@ComponentScan("com.git.hui.cloud.api.eurka")
@Configuration
public class ApiConfiguration {

    public static final String SERVICE_NAME = "eureka-service-provider";

    // 自动配置feign扫描包 使用方零配置 微服务本身不加载自己的API中的feign
    @ConditionalOnExpression("#{!environment['spring.application.name'].endsWith('" + SERVICE_NAME + "')}")
    @EnableFeignClients("com.git.hui.cloud.api.eurka")
    public class FeignConfig {
    }
}
