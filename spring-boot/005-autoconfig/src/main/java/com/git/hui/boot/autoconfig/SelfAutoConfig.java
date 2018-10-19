package com.git.hui.boot.autoconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by @author yihui in 16:59 18/10/10.
 */
@Configuration
@ComponentScan("com.git.hui.boot.autoconfig")
public class SelfAutoConfig {

    @Bean
    public AutoConfBean autoConfBean() {
        return new AutoConfBean("auto load + " + System.currentTimeMillis());
    }
}
