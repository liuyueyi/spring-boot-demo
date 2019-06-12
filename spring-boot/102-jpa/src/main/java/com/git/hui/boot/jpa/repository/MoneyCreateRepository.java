package com.git.hui.boot.jpa.repository;

import com.git.hui.boot.jpa.entity.MoneyPO;
import org.springframework.data.repository.CrudRepository;

/**
 * 新增数据
 * Created by @author yihui in 11:00 19/6/12.
 */
public interface MoneyCreateRepository extends CrudRepository<MoneyPO, Integer> {
}
