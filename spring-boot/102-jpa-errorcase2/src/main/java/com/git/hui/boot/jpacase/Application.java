package com.git.hui.boot.jpacase;

import com.git.hui.boot.jpacase.strategy.JpaNamingStrategyStandardImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by @author yihui in 17:11 19/12/30.
 */
@SpringBootApplication
public class Application {
    public Application(GroupManager groupManager) {
        groupManager.test();
    }

    public static void main(String[] args) {
        JpaNamingStrategyStandardImpl.setMode(1);
        SpringApplication.run(Application.class, args);
    }

}
