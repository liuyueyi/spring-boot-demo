package com.git.hui.boot.async;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

/**
 * Created by @author yihui in 16:41 20/3/29.
 */
@Configuration
@EnableWebMvc
public class WebConf implements WebMvcConfigurer {

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        // 超时时间设置为60s
        configurer.setDefaultTimeout(TimeUnit.SECONDS.toMillis(10));
    }
}
