package com.git.hui.boot.jpa.repository;

import com.git.hui.boot.jpa.entity.AutoMoneyPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 新增数据
 * Created by @author yihui in 11:00 19/6/12.
 */
public interface MoneyCreateRepositoryWithId extends JpaRepository<AutoMoneyPO, Integer> {
}
