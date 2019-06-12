package com.git.hui.boot.jpa.demo;

import com.git.hui.boot.jpa.entity.MoneyPO;
import com.git.hui.boot.jpa.repository.MoneyCreateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Arrays;

/**
 * Created by @author yihui in 11:00 19/6/12.
 */
@Component
public class JpaInsertDemo {
    @Autowired
    private MoneyCreateRepository moneyCreateRepository;

    public void testInsert() {
        addOne();
        addMutl();

        addWithNull();
        addWithId();
    }


    private void addOne() {
        // 单个添加
        MoneyPO moneyPO = new MoneyPO();
        moneyPO.setName("jpa 一灰灰");
        moneyPO.setMoney(1000L);
        moneyPO.setIsDeleted((byte) 0x00);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        moneyPO.setCreateAt(now);
        moneyPO.setUpdateAt(now);

        MoneyPO res = moneyCreateRepository.saveAndFlush(moneyPO);
        System.out.println("after insert res: " + res);
    }


    private void addMutl() {
        // 批量添加
        MoneyPO moneyPO = new MoneyPO();
        moneyPO.setName("batch jpa 一灰灰");
        moneyPO.setMoney(1000L);
        moneyPO.setIsDeleted((byte) 0x00);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        moneyPO.setCreateAt(now);
        moneyPO.setUpdateAt(now);

        MoneyPO moneyPO2 = new MoneyPO();
        moneyPO2.setName("batch jpa 一灰灰");
        moneyPO2.setMoney(1000L);
        moneyPO2.setIsDeleted((byte) 0x00);
        moneyPO2.setCreateAt(now);
        moneyPO2.setUpdateAt(now);

        Iterable<MoneyPO> res = moneyCreateRepository.saveAll(Arrays.asList(moneyPO, moneyPO2));
        moneyCreateRepository.flush();
        System.out.println("after batchAdd res: " + res);
    }


    private void addWithNull() {
        // 单个添加
        try {
            MoneyPO moneyPO = new MoneyPO();
            moneyPO.setName("jpa 一灰灰 ex");
            moneyPO.setMoney(2000L);
            moneyPO.setIsDeleted(null);
            MoneyPO res = moneyCreateRepository.saveAndFlush(moneyPO);
            System.out.println("after insert res: " + res);
        } catch (Exception e) {
            System.out.println("addWithNull field: " + e.getMessage());
        }
    }

    private void addWithId() {
        // 单个添加
        MoneyPO moneyPO = new MoneyPO();
        moneyPO.setId(20);
        moneyPO.setName("jpa 一灰灰 ex");
        moneyPO.setMoney(2200L);
        moneyPO.setIsDeleted((byte) 0x00);
        MoneyPO res = moneyCreateRepository.saveAndFlush(moneyPO);
        System.out.println("after insert res: " + res);
    }
}
