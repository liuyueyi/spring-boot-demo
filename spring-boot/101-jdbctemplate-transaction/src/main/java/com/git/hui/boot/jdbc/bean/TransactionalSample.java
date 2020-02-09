package com.git.hui.boot.jdbc.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 10:42 20/1/19.
 */
@Component
public class TransactionalSample {
    @Autowired
    private SimpleDemo simpleService;

    @Autowired
    private ManualDemo manualDemo;

    public void testSimpleCase() {
        // 不会生效，因为test方法上没有事务注解
        simpleService.test();

        System.out.println("============ 事务正常工作 start ========== ");
        simpleService.query("transaction before", 130);
        try {
            // 事务可以正常工作
            simpleService.testRuntimeExceptionTrans(130);
        } catch (Exception e) {
        }
        simpleService.query("transaction end", 130);
        System.out.println("============ 事务正常工作 end ========== \n");


        System.out.println("============ 事务不生效 start ========== ");
        simpleService.query("transaction before", 140);
        try {
            // 因为抛出的是非运行异常，不会回滚
            simpleService.testNormalException(140);
        } catch (Exception e) {
        }
        simpleService.query("transaction end", 140);
        System.out.println("============ 事务不生效 end ========== \n");


        System.out.println("============ 事务生效 start ========== ");
        simpleService.query("transaction before", 150);
        try {
            // 注解中，指定所有异常都回滚
            simpleService.testSpecialException(150);
        } catch (Exception e) {
        }
        simpleService.query("transaction end", 150);
        System.out.println("============ 事务生效 end ========== \n");
    }


    public void testManualCase() {
        System.out.println("======= 编程式事务 start ========== ");
        manualDemo.query("transaction before", 220);
        manualDemo.testTransaction(220);
        manualDemo.query("transaction end", 220);
        System.out.println("======= 编程式事务 end ========== ");
    }
}
