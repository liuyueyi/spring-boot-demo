package com.git.hui.boot.web;

import com.git.hui.boot.web.filter.OrderFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by @author yihui in 15:49 19/10/12.
 */
@ServletComponentScan
@SpringBootApplication
public class Application {

    @Bean
    public FilterRegistrationBean<OrderFilter> orderFilter() {
        FilterRegistrationBean<OrderFilter> filter = new FilterRegistrationBean<>();
        filter.setName("orderFilter");
        filter.setFilter(new OrderFilter());
        filter.setOrder(-1);
        return filter;
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
