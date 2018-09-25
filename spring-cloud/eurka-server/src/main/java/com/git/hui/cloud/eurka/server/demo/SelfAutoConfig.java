package com.git.hui.cloud.eurka.server.demo;

import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by @author yihui in 18:19 18/9/7.
 */
@Configuration
@AutoConfigureOrder(100)
public class SelfAutoConfig {
    @Bean
    public SelfBean selfBean() {
        return new SelfBean();
    }
}
