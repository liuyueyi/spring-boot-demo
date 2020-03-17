package com.git.hui.boot.client;

import com.git.hui.boot.client.load.DatasourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Created by @author yihui in 11:43 20/2/28.
 */
@Configuration
public class ClientAutoConfiguration {
    @Bean
    public DatasourceLoader propertyLoader(Environment environment) {
        return new DatasourceLoader(environment);
    }
}
