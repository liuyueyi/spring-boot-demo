package com.git.hui.boot.multi.datasource.test.mapper;

import com.git.hui.boot.multi.datasource.test.entity.TestMoneyEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yihui
 * @date 20/12/27
 */
@Mapper
public interface TestMoneyMapper {
    List<TestMoneyEntity> findByIds(List<Integer> ids);
}
