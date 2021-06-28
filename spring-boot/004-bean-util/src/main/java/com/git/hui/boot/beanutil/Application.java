package com.git.hui.boot.beanutil;

import com.git.hui.boot.beanutil.bench.CopierTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yihui
 * @date 2021/4/7
 */
@SpringBootApplication
public class Application {
    public Application(CopierTest copierTest) throws Exception {
        copierTest.test();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
