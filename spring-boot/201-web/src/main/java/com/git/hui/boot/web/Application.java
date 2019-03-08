package com.git.hui.boot.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Created by @author yihui in 22:01 19/2/12.
 */
@SpringBootApplication
public class Application {

    public Application(RestTemplate restTemplate) {
        String ans = restTemplate.getForObject("https://story.hhui.top/", String.class);
        System.out.println(ans);


        try {
            ans = restTemplate.getForObject("https://47.98.136.120/", String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
