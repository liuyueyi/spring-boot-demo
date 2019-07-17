package com.git.hui.boot.jpa.demo;

import com.git.hui.boot.jpa.entity.MoneyPO;
import com.git.hui.boot.jpa.repository.MoneyBaseQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by @author yihui in 09:01 19/6/12.
 */
@Component
public class JpaQueryDemo {
    @Autowired
    private MoneyBaseQueryRepository moneyCurdRepository;

    public void queryTest() {
        queryById();
        queryByField();
        queryByLike();
        queryWithIn();
        queryByCompare();
        queryWithSort();
        queryWithPageSize();
    }


    private void queryById() {
        // 根据主键查询，直接使用接口即可
        Optional<MoneyPO> res = moneyCurdRepository.findById(1);
        System.out.println("queryById return: " + res.get());
    }

    private void queryByField() {
        // 根据内部成员进行查询，需要自己定义新的接口
        String name = "一灰灰blog";
        Iterable<MoneyPO> res = moneyCurdRepository.findByName(name);
        System.out.println("findByName return: " + res);

        res = moneyCurdRepository.queryByName(name);
        System.out.println("queryByName return: " + res);

        Long money = 100L;
        res = moneyCurdRepository.findByNameAndMoney(name, money);
        System.out.println("findByNameAndMoney return: " + res);

        Integer id = 5;
        res = moneyCurdRepository.findByNameOrId(name, id);
        System.out.println("findByNameOrId return: " + res);
    }

    private void queryByLike() {
        // like 语句查询
        String name = "一灰灰%";
        Iterable<MoneyPO> res = moneyCurdRepository.findByNameLike(name);
        System.out.println("findByName like: " + res);
    }

    private void queryWithIn() {
        // in 查询
        List<Integer> ids = Arrays.asList(1, 2, 3);
        Iterable<MoneyPO> res = moneyCurdRepository.findAllById(ids);
        System.out.println("findByIds return: " + res);

        res = moneyCurdRepository.findByMoneyIn(Arrays.asList(400L, 300L));
        System.out.println("findByMoneyIn return: " + res);
    }

    private void queryByCompare() {
        Integer id1 = 3;
        Iterable<MoneyPO> res = moneyCurdRepository.findByIdLessThanEqual(id1);
        System.out.println("findByIdLessThan 3 return: " + res);


        Integer id2 = 10;
        res = moneyCurdRepository.findByIdGreaterThanEqual(id2);
        System.out.println("findByIdGreaterThan 10 return: " + res);

        id1 = 4;
        id2 = 6;
        res = moneyCurdRepository.findByIdIsBetween(id1, id2);
        System.out.println("findByIdsWBetween 3, 10 return: " + res);
    }

    private void queryWithSort() {
        // 排序
        Long money = 400L;
        Iterable<MoneyPO> res = moneyCurdRepository.findByMoneyOrderByIdDesc(money);
        System.out.println("findByMoneyAndOrderByIdDesc return: " + res);

        Integer startId = 7;
        res = moneyCurdRepository.queryByIdGreaterThanEqualOrderByMoneyDescIdAsc(startId);
        System.out.println("queryByIdGreaterThanEqualOrderByMoneyDescIdAsc return: " + res);

    }

    private void queryWithPageSize() {
        // 分页查询
        Iterable<MoneyPO> res = moneyCurdRepository.findTop3ByIdGreaterThan(3);
        System.out.println("findTop3ByIdGreaterThan 3 return: " + res);

        res = moneyCurdRepository.findByIdGreaterThan(3, PageRequest.of(2, 3));
        System.out.println("findByIdGreaterThan 3 pageIndex 2 size 3 return: " + res);
    }

}
