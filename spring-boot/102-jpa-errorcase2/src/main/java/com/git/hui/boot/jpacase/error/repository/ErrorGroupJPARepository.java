package com.git.hui.boot.jpacase.error.repository;

import com.git.hui.boot.jpacase.error.entity.ErrorMetaGroupPo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by @author yihui in 17:16 19/12/30.
 */
public interface ErrorGroupJPARepository extends JpaRepository<ErrorMetaGroupPo, Integer> {
}