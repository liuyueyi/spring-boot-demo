package com.git.hui.boot.jpa;

import com.git.hui.boot.jpa.entity.MoneyPO;
import com.git.hui.boot.jpa.query.MoneyQueryRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by @author yihui in 20:58 19/6/10.
 */
@SpringBootApplication
public class Application {
    public Application(MoneyQueryRepository moneyQueryRepository) {
        MoneyPO moneyPO = moneyQueryRepository.findById(1).get();
        System.out.println(moneyPO);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }


}
