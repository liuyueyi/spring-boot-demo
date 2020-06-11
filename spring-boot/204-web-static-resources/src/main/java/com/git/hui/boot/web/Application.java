package com.git.hui.boot.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by @author yihui in 08:43 20/6/10.
 */
@SpringBootApplication
public class Application implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 请注意下面这个映射，将资源路径 /ts 下的资源，映射到根目录为 /ts的访问路径下
        // 如 ts下的ts.html, 对应的访问路径为   /ts/ts
        registry.addResourceHandler("/ts/**").addResourceLocations("classpath:/ts/");
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
