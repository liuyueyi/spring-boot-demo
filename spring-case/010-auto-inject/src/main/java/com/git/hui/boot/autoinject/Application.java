package com.git.hui.boot.autoinject;

import com.git.hui.boot.autoinject.example.RestService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yihui
 * @date 2021/2/9
 */
@SpringBootApplication
public class Application {

    public Application(RestService restService) {
        restService.test();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
