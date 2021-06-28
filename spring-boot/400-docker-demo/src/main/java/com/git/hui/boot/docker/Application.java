package com.git.hui.boot.docker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author yihui
 * @date 2021/2/22
 */
@SpringBootApplication
@RestController
public class Application {

    @GetMapping(path = {"", "/"})
    public String hello() {
        return "hello " + UUID.randomUUID();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
