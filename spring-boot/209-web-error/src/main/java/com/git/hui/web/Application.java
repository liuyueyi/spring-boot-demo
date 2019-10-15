package com.git.hui.web;

import com.git.hui.web.global.SelfExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Created by @author yihui in 15:26 19/9/13.
 */
@SpringBootApplication
public class Application implements WebMvcConfigurer {

    /**
     * 说明，使用SelfExceptionHandler时， GlobalExceptionHandler将不会生效； 在测试后者时，可以把下面这个方法注释掉
     *
     * @param resolvers
     */
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(0, new SelfExceptionHandler());
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
