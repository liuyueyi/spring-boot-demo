package com.git.hui.boot.redisson;

import com.git.hui.boot.redisson.demo.IllegalLockSample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yihui
 * @date 2021/3/1
 */
@RestController
@SpringBootApplication
public class Application {
    @Autowired
    private IllegalLockSample lockSample;

    @GetMapping(path = "")
    public String lock() {
        lockSample.testLock();
        return "over";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
