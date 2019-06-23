package com.git.hui.boot.jpa.demo;

import com.git.hui.boot.jpa.entity.MoneyPO;
import com.git.hui.boot.jpa.repository.MoneyUpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by @author yihui in 20:55 19/6/22.
 */
@Component
public class JpaUpdateDemo {

    @Autowired
    private MoneyUpdateRepository moneyUpdateRepository;

    @Transactional
    public void testUpdate() {
//        simpleUpdateById();
        updateByQuery();
    }

    public void simpleUpdateById() {
        MoneyPO record = moneyUpdateRepository.findById(21).get();
        // 直接修改这个record的内容
        record.setMoney(3333L);
        moneyUpdateRepository.save(record);

        record = moneyUpdateRepository.findById(21).get();
        System.out.println("after updateMoney record: " + record);

        try {
            MoneyPO toUpdate = new MoneyPO();
            toUpdate.setId(21);
            toUpdate.setMoney(6666L);
            moneyUpdateRepository.save(toUpdate);
            record = moneyUpdateRepository.findById(21).get();
            System.out.println("after updateMoney record: " + record);
        } catch (Exception e) {
            e.printStackTrace();
        }

        record.setMoney(6666L);
        moneyUpdateRepository.save(record);
    }

    public void updateByQuery() {
        // 通过查询修改
        moneyUpdateRepository.updateStateByMoney(6666L, (byte) 0x01);
        moneyUpdateRepository.flush();

        MoneyPO record = moneyUpdateRepository.findById(21).get();
        System.out.println("after update record: " + record);


        moneyUpdateRepository.addMoneyById(21, 3333L);
        moneyUpdateRepository.flush();
        record = moneyUpdateRepository.findById(21).get();
        System.out.println("after addMoney record: " + record);
    }
}
