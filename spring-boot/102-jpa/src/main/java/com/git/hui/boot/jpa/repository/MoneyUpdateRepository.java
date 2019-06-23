package com.git.hui.boot.jpa.repository;

import com.git.hui.boot.jpa.entity.MoneyPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by @author yihui in 11:08 19/6/12.
 */
public interface MoneyUpdateRepository extends JpaRepository<MoneyPO, Integer> {

    /**
     * 根据金钱来修改状态
     *
     * @param money
     * @param state
     */
    @Modifying
    @Query("update MoneyPO m set m.isDeleted=?2 where  m.money=?1")
    void updateStateByMoney(Long money, Byte state);


    /**
     * 表达式计算
     *
     * @param id
     * @param money
     */
    @Modifying
    @Query("update MoneyPO m set m.money=m.money + ?2 where m.id=?1")
    void addMoneyById(Integer id, Long money);
}
