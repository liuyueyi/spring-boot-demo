package com.git.hui.boot.jdbc;

import com.git.hui.boot.jdbc.bean.DetailTransactionalSample;
import com.git.hui.boot.jdbc.bean.PropagationSample;
import com.git.hui.boot.jdbc.bean.TransactionalSample;
import com.git.hui.boot.jdbc.demo.NotEffectSample;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by @author yihui in 14:33 20/1/17.
 */
@SpringBootApplication
public class Application {
    public Application(NotEffectSample notEffectSample, TransactionalSample transactionalSample,
            DetailTransactionalSample detailTransactionalSample, PropagationSample propagationSample) throws Exception {
        transactionalSample.testSimpleCase();
        transactionalSample.testManualCase();

        notEffectSample.testNotEffect();


        detailTransactionalSample.testIsolation();

        propagationSample.testPropagation();

    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}