package com.git.hui.boot.scheduler;

import com.git.hui.boot.scheduler.ano.EnableDistributeScheduling;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by @author yihui in 20:25 20/4/10.
 */
@SpringBootApplication
@EnableDistributeScheduling
//@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
