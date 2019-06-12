package com.git.hui.boot.jpa.repository;

import com.git.hui.boot.jpa.entity.MoneyPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by @author yihui in 21:01 19/6/10.
 */
public interface MoneyDemoRepository extends JpaRepository<MoneyPO, Integer> {
}
