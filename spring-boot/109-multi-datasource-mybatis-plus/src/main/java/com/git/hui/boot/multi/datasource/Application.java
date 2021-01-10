package com.git.hui.boot.multi.datasource;

import com.git.hui.boot.multi.datasource.entity.MoneyPo;
import com.git.hui.boot.multi.datasource.service.impl.StoryMoneyServiceImpl;
import com.git.hui.boot.multi.datasource.service.impl.TestMoneyServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

/**
 * @author yihui
 * @date 20/12/27
 */
@SpringBootApplication
@MapperScan("com.git.hui.boot.multi.datasource.mapper")
public class Application {

    public Application(TestMoneyServiceImpl testMoneyService, StoryMoneyServiceImpl storyMoneyService) {
        List<MoneyPo> moneyPoList = testMoneyService.listByIds(Arrays.asList(1, 1000));
        System.out.println(moneyPoList);
        System.out.println("--------------");

        moneyPoList = storyMoneyService.listByIds(Arrays.asList(1, 1000));
        System.out.println(moneyPoList);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
