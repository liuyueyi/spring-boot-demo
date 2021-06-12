package com.git.hui.boot.dynamic.env;

import com.git.hui.boot.dynamic.env.config.FilePropertiesSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author yihui
 * @date 21/6/10
 */
@Configuration
public class AutoConfig {
    @Bean
    public FilePropertiesSource filePropertiesSource(ConfigurableEnvironment environment) {
        FilePropertiesSource filePropertiesSource = new FilePropertiesSource();
        environment.getPropertySources().addLast(filePropertiesSource);
        return filePropertiesSource;
    }
}
