package com.git.hui.boot.jpacase.right.repository;

import com.git.hui.boot.jpacase.right.entity.MetaGroupPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by @author yihui in 19:12 19/12/16.
 */
public interface GroupJPARepository extends JpaRepository<MetaGroupPO, Integer> {

}
