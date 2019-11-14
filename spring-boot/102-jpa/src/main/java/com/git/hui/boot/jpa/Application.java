package com.git.hui.boot.jpa;

import com.git.hui.boot.jpa.demo.JpaDeleteDemo;
import com.git.hui.boot.jpa.demo.JpaInsertDemo;
import com.git.hui.boot.jpa.demo.JpaQueryDemo;
import com.git.hui.boot.jpa.demo.JpaUpdateDemo;
import com.git.hui.boot.jpa.entity.MoneyPO;
import com.git.hui.boot.jpa.repository.MoneyDemoRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by @author yihui in 20:58 19/6/10.
 */
@SpringBootApplication
public class Application {
    public Application(MoneyDemoRepository moneyDemoRepository, JpaQueryDemo jpaQueryDemo, JpaInsertDemo jpaInsertDemo,
            JpaUpdateDemo jpaUpdateDemo, JpaDeleteDemo jpaDeleteDemo) {
        //        MoneyPO moneyPO = moneyDemoRepository.findById(1).get();
        //        System.out.println(moneyPO);
        //        System.out.println("-----------------------");

//                jpaQueryDemo.queryTest();

                jpaInsertDemo.testInsert();
        //        jpaUpdateDemo.testUpdate();

//        jpaDeleteDemo.testDelete();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
