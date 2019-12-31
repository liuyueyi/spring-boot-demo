package com.git.hui.boot.mybatis;

import com.git.hui.boot.mybatis.demo.MoneyRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by @author yihui in 16:29 19/12/25.
 */
@SpringBootApplication
public class Application {

    public Application(MoneyRepository repository) {
        repository.testMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
