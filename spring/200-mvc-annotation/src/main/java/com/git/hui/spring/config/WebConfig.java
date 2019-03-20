package com.git.hui.spring.config;

import com.git.hui.spring.hook.intercept.MyRefererInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by @author yihui in 18:04 19/3/15.
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(new MyRefererInterceptor());
        addInterceptor.excludePathPatterns("/hello");
        addInterceptor.addPathPatterns("/**");
    }
}
