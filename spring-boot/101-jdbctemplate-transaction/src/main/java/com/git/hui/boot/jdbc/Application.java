package com.git.hui.boot.jdbc;

import com.git.hui.boot.jdbc.api.TransApiImpl;
import com.git.hui.boot.jdbc.bean.DetailTransactionalSample;
import com.git.hui.boot.jdbc.bean.PropagationSample;
import com.git.hui.boot.jdbc.bean.TransactionalSample;
import com.git.hui.boot.jdbc.demo.NotEffectSample;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by @author yihui in 14:33 20/1/17.
 */
@SpringBootApplication
public class Application {
    public Application(NotEffectSample notEffectSample, TransactionalSample transactionalSample,
            DetailTransactionalSample detailTransactionalSample, PropagationSample propagationSample,
                       TransApiImpl transApi, JdbcTemplate jdbcTemplate) throws Exception {
        transactionalSample.testSimpleCase();
        transactionalSample.testManualCase();

        notEffectSample.testNotEffect();


        detailTransactionalSample.testIsolation();

        propagationSample.testPropagation();

        try {
            transApi.update(111);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(jdbcTemplate.queryForList("select * from money where id=111"));
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}