package com.git.hui.boot.mybatisplus;

import com.git.hui.boot.mybatisplus.demo.MoneyRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by @author yihui in 14:41 19/12/26.
 */
@SpringBootApplication
//@MapperScan("com.git.hui.boot.mybatisplus.mapper")
public class Application {

    public Application(MoneyRepository moneyRepository) {
        moneyRepository.testDemo();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
