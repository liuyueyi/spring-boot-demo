package com.git.hui.boot.jpa.repository;

import com.git.hui.boot.jpa.entity.MoneyPO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * 更灵活的查询使用姿势
 *
 * Created by @author yihui in 08:59 19/6/12.
 */
public interface MoneyExtendQueryRepository extends CrudRepository<MoneyPO, Integer> {

}
