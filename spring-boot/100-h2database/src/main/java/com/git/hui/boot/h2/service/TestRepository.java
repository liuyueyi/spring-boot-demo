package com.git.hui.boot.h2.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by @author yihui in 08:57 20/9/9.
 */
@Repository
public interface TestRepository extends CrudRepository<TestEntity, Integer> {
}
