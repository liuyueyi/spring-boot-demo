package com.git.hui.boot.mybatisplus.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.git.hui.boot.mybatisplus.entity.MoneyPo;
import com.git.hui.boot.mybatisplus.mapper.MoneyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by @author yihui in 14:46 19/12/26.
 */
@Component
public class MoneyRepository {
    @Autowired
    private MoneyMapper moneyMapper;

    private Random random = new Random();

    public void testDemo() {
        MoneyPo po = new MoneyPo();
        po.setName("mybatis plus user");
        po.setMoney((long) random.nextInt(12343));
        po.setIsDeleted(0);

        // 添加一条数据
        moneyMapper.insert(po);

        // 查询
        List<MoneyPo> list =
                // 与下面等价 new QueryWrapper<MoneyPo>().eq("name", po.getName())
                moneyMapper.selectList(new QueryWrapper<MoneyPo>().lambda().eq(MoneyPo::getName, po.getName()));
        System.out.println("after insert: " + list);

        // 修改
        po.setMoney(po.getMoney() + 300);
        moneyMapper.updateById(po);
        System.out.println("after update: " + moneyMapper.selectById(po.getId()));

        // 删除
        moneyMapper.deleteById(po.getId());

        // 查询
        Map<String, Object> queryMap = new HashMap<>(2);
        queryMap.put("name", po.getName());
        System.out.println("after delete: " + moneyMapper.selectByMap(queryMap));
    }
}
