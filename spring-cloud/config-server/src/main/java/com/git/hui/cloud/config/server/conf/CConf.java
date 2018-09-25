package com.git.hui.cloud.config.server.conf;

import com.git.hui.cloud.config.server.demo.CDemo;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by @author yihui in 22:19 18/9/6.
 */
@Configuration
@AutoConfigureOrder(10)
public class CConf {
    @Bean
    public CDemo cDemo() {
        System.out.println("cconf");
        return new CDemo();
    }
}
