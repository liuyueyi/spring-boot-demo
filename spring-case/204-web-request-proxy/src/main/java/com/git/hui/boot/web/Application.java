package com.git.hui.boot.web;

import com.git.hui.boot.web.anno.ApiScanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wuzebang
 * @date 2021/3/5
 */
@ApiScanner
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
