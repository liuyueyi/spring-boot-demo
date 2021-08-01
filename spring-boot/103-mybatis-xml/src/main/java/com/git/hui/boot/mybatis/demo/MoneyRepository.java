package com.git.hui.boot.mybatis.demo;

import com.git.hui.boot.mybatis.entity.MoneyPo;
import com.git.hui.boot.mybatis.mapper.MoneyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by @author yihui in 15:24 19/12/25.
 */
@Repository
public class MoneyRepository {
    @Autowired
    private MoneyMapper moneyMapper;

    public void testBasic() {
        MoneyPo po = new MoneyPo();
        po.setName("mybatis user");
        po.setMoney((long) random.nextInt(12343));
        po.setIsDeleted(0);

        moneyMapper.savePo(po);
        System.out.println(po);
        MoneyPo out = moneyMapper.findById(po.getId());
        System.out.println("query:" + out);
        moneyMapper.addMoney(po.getId(), 100);
        System.out.println("after update:" + moneyMapper.findById(po.getId()));
        moneyMapper.delPo(po.getId());
        System.out.println("after del:" + moneyMapper.findById(po.getId()));
    }

    private Random random = new Random();

    public void testMapper() {
        MoneyPo po = new MoneyPo();
        po.setName("mybatis user");
        po.setMoney((long) random.nextInt(12343));
        po.setIsDeleted(0);

        moneyMapper.savePo(po);
        System.out.println("add record: " + po);
        moneyMapper.addMoney(po.getId(), 200);
        System.out.println("after addMoney: " + moneyMapper.findByName(po.getName()));
        moneyMapper.delPo(po.getId());
        System.out.println("after delete: " + moneyMapper.findByName(po.getName()));
        Long id = moneyMapper.findIdById(100);
        System.out.println(id);

        // 批量保存，如果有不满足条件的可以忽略 --> 对于唯一键冲突，不做任何处理； 长度超限，截取
        List<MoneyPo> batchList = Arrays.asList(new MoneyPo("tt", 11L, 0),
                new MoneyPo("mybatis user", 12L, 0),
                new MoneyPo("hello world test this name is too long for sub one", 11L, 0),
                new MoneyPo("haha", 122L, 0));
        int ans = moneyMapper.batchSave(batchList);
        System.out.println(ans);
    }

    @Transactional(rollbackFor = Exception.class)
    public void testFirstCache(int id) {
        dupQuery(id);
    }

    public void dupQuery(int id) {
        MoneyPo po = moneyMapper.findById(id);
        System.out.println("\n>>>>>>>>>>>>" + po + " | " + System.identityHashCode(po) + "\n");

        po = moneyMapper.findById(id);
        System.out.println("\n>>>>>>>>>>>>" + po + " | " + System.identityHashCode(po) + "\n");

        po = moneyMapper.findById(id);
        System.out.println("\n>>>>>>>>>>>>" + po + " | " + System.identityHashCode(po) + "\n");
        System.out.println("\n\n<<<<<<<<< over >>>>>>>>>>>>>\n\n");
    }
}
