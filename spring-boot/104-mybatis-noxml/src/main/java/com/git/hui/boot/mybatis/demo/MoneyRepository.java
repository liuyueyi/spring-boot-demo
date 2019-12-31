package com.git.hui.boot.mybatis.demo;

import com.git.hui.boot.mybatis.entity.MoneyPo;
import com.git.hui.boot.mybatis.mapper.MoneyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Random;

/**
 * Created by @author yihui in 15:24 19/12/25.
 */
@Repository
public class MoneyRepository {
    @Autowired
    private MoneyMapper moneyMapper;

    private Random random = new Random();

    public void testMapper() {
        MoneyPo po = new MoneyPo();
        po.setName("mybatis noxml user");
        po.setMoney((long) random.nextInt(12343));
        po.setIsDeleted(0);

        moneyMapper.savePo(po);
        System.out.println("add record: " + po);
        moneyMapper.addMoney(po.getId(), 200);
        System.out.println("after update: " + moneyMapper.findByName(po.getName()));
        moneyMapper.delPo(po.getId());
        System.out.println("after delete: " + moneyMapper.findByName(po.getName()));
    }
}
