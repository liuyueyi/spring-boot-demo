package com.git.hui.boot.beanorder;

import com.git.hui.boot.beanorder.choose.samename.a.SameA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;


/**
 * Created by @author yihui in 18:20 18/10/22.
 */
@SpringBootApplication
@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SameA.class)})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
