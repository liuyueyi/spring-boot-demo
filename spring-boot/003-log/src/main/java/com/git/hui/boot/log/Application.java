package com.git.hui.boot.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by @author yihui in 22:12 18/9/25.
 */
@Slf4j
@SpringBootApplication
public class Application {

    public Application() {
        log.debug("---> debug start! <------");
        log.info("---> info start! <------");
        log.warn("---> warn start! <------");
        log.error("---> conf start! <------");

        System.out.println("===> System.out.println <=====");
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
