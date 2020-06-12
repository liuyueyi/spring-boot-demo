package com.git.hui.boot.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * Created by @author yihui in 18:51 18/10/19.
 */
@SpringBootApplication
public class Application implements WebFluxConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/o2/");
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
