package com.git.hui.boot.client;

import com.git.hui.boot.client.load.PropertyLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Created by @author yihui in 11:43 20/2/28.
 */
@Configuration
public class ClientAutoConfiguration {
    @Bean
    public PropertyLoader propertyLoader(Environment environment) {
        return new PropertyLoader(environment);
    }
}
