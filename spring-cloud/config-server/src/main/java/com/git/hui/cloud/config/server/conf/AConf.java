package com.git.hui.cloud.config.server.conf;

import com.git.hui.cloud.config.server.demo.ADemo;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by @author yihui in 22:19 18/9/6.
 */
@Configuration
@AutoConfigureOrder(2)
public class AConf {
    @Bean
    public ADemo aDemo() {
        System.out.println("aconf");
        return new ADemo();
    }
}
