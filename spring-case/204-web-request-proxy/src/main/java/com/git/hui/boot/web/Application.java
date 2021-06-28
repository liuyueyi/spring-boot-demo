package com.git.hui.boot.web;

import com.git.hui.boot.web.anno.ApiScanner;
import com.git.hui.boot.web.util.EnvironmentUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

/**
 * @author yihui
 * @date 2021/3/5
 */
@ApiScanner
@SpringBootApplication
public class Application {
    public Application(Environment environment) {
        EnvironmentUtil.setEnvironment(environment);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
