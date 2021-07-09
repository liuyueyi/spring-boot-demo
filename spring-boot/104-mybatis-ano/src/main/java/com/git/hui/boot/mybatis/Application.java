package com.git.hui.boot.mybatis;

import com.git.hui.boot.mybatis.service.MoneyService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yihui
 * @date 2021/7/6
 */
//@MapperScan(basePackages = "com.git.hui.boot.*.mapper")
@SpringBootApplication
public class Application {

    public Application(MoneyService moneyService) {
        moneyService.basicTest();
//        moneyService.testProvider();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
