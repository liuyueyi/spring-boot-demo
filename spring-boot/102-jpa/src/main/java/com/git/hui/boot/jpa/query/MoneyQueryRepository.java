package com.git.hui.boot.jpa.query;

import com.git.hui.boot.jpa.entity.MoneyPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by @author yihui in 21:01 19/6/10.
 */
@Repository
public interface MoneyQueryRepository extends JpaRepository<MoneyPO, Integer> {
}
