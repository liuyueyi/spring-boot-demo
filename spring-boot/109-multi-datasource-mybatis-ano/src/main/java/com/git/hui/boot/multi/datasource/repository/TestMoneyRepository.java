package com.git.hui.boot.multi.datasource.repository;

import com.git.hui.boot.multi.datasource.entity.TestMoneyEntity;
import com.git.hui.boot.multi.datasource.mapper.TestMoneyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * @author yihui
 * @date 20/12/27
 */
@Repository
public class TestMoneyRepository {
    @Autowired
    private TestMoneyMapper testMoneyMapper;

    public void query() {
        List<TestMoneyEntity> list = testMoneyMapper.findByIds(Arrays.asList(1, 1000));
        System.out.println(list);
    }
}
