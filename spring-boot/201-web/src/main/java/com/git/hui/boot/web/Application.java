package com.git.hui.boot.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

/**
 * Created by @author yihui in 22:01 19/2/12.
 */
@SpringBootApplication
public class Application {

    public Application(RestTemplate restTemplate) {
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
