package com.git.hui.boot.mybatis.demo;

import com.git.hui.boot.mybatis.entity.MoneyPo;
import com.git.hui.boot.mybatis.mapper.MoneyMapper;
import com.git.hui.boot.mybatis.mapper.MoneyMapperV2;
import com.git.hui.boot.mybatis.mapper.MoneyMapperV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by @author yihui in 15:24 19/12/25.
 */
@Repository
public class MoneyRepository {
    @Autowired
    private MoneyMapper moneyMapper;
    @Autowired
    private MoneyMapperV2 moneyMapperV2;
    @Autowired
    private MoneyMapperV3 moneyMapperV3;

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

    /**
     * 用于验证某些场景下，只能使用 ${}
     */
    public void groupBy() {
        List<MoneyPo> list = moneyMapperV2.groupBy("name");
        System.out.println(list);
    }


    /**
     * 测试枚举类型的case
     *
     * 知识点：
     * 枚举类型，传入的参数，应该为String类型；如果是int，则表示根据枚举的下标索引来检索
     *  - 如枚举字段 bank ('2', '3', '1')
     *  - 传参 1 对应的是 '2'， 传参 '1' 对应的则是 '1'
     */
    public void testEnumQuery() {
        // 枚举类型的查询，虽然定义的是字符串，但是传参为数字时，会有问题
        List list = moneyMapperV2.queryByBank(1);
        System.out.println(list);

    }

    /**
     * 用于测试，mybatis中 #{} 替换的参数类型，是否会转换为String类型
     */
    public void testArgumentReplace() {
        List list = moneyMapperV3.queryByName(1);
        System.out.println(list);

        list = moneyMapperV3.queryByNameV2(1);
        System.out.println(list);

        list = moneyMapperV3.queryByNameV3("1");
        System.out.println(list);

        list = moneyMapperV3.queryByNameV4(1);
        System.out.println(list);
    }

    /**
     * 位查询
     */
    public void testByteQuery() {
        // xml 使用 <![CDATA[  ]]> 内部可以填写原始的表达式，不需要额外的转移符
        // 其他的转移:
        // &lt; == < 小于号
        // &gt; == > 大于号
        // &amp; == & 与符号
        // &apos; == ' 单引号
        // &quot; == " 双引号
//        List<Long> ids = moneyMapper.queryBitCondition(1);
//        System.out.println(ids);

        Map<String, Object> map = new HashMap<>();
        map.put("name", "tt");
        List list = moneyMapper.findByIdOrCondition(1, map);
        System.out.println(list);

        list = moneyMapper.findByIdOrConditionV2(1, map);
        System.out.println(list);
    }
}
