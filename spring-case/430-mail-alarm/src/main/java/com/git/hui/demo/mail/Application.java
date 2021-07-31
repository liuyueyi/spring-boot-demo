package com.git.hui.demo.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yihui
 * @date 21/7/31
 */
@Slf4j
@RestController
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @GetMapping("div")
    public String div(int a, int b) {
        try {
            return String.valueOf(a / b);
        } catch (Exception e) {
            log.error("div error! {}/{}", a, b, e);
            return "some error!";
        }
    }

}
