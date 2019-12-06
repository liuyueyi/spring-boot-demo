package com.git.hui.boot.web.listener;

import com.git.hui.boot.web.listener.listener.ConfigContextListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Created by @author yihui in 11:20 19/12/6.
 */
@ServletComponentScan
@SpringBootApplication
public class Application {

    @Bean
    public ServletListenerRegistrationBean configContextListener() {
        ServletListenerRegistrationBean bean = new ServletListenerRegistrationBean();
        bean.setListener(new ConfigContextListener());
        return bean;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
