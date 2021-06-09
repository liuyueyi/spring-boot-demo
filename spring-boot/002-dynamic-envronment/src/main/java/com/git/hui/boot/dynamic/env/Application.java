package com.git.hui.boot.dynamic.env;

import com.git.hui.boot.dynamic.env.config.FilePropertiesSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yihui
 * @date 2021/6/9
 */
@EnableScheduling
@RestController
@SpringBootApplication
public class Application {
    private Environment environment;

    @Bean
    public FilePropertiesSource filePropertiesSource(ConfigurableEnvironment environment) {
        this.environment = environment;
        FilePropertiesSource filePropertiesSource = new FilePropertiesSource();
        environment.getPropertySources().addLast(filePropertiesSource);
        return filePropertiesSource;
    }

    @GetMapping(path = "get")
    public String getProperty(String key) {
        return environment.getProperty(key);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
