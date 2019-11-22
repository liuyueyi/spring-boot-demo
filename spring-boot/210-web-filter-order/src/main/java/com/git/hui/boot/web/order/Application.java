package com.git.hui.boot.web.order;

import com.git.hui.boot.web.order.filter.ReqFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;

/**
 * Created by @author yihui in 17:47 19/10/17.
 */
@ServletComponentScan
@SpringBootApplication
public class Application {

    public Application(ApplicationContext applicationContext) {
        try {
            Object obj = applicationContext.getBean("reqFilter");
            Object obj2 = applicationContext.getBean(ReqFilter.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
