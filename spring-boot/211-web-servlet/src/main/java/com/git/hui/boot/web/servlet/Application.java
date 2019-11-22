package com.git.hui.boot.web.servlet;

import com.git.hui.boot.web.servlet.servlet.RegisterBeanServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Created by @author yihui in 18:12 19/11/21.
 */
@ServletComponentScan
@SpringBootApplication
public class Application {

    @Bean
    public ServletRegistrationBean servletBean() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.addUrlMappings("/register");
        registrationBean.setServlet(new RegisterBeanServlet());
        return registrationBean;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
