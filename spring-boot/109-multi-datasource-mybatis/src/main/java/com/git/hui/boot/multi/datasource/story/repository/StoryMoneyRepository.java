package com.git.hui.boot.multi.datasource.story.repository;

import com.git.hui.boot.multi.datasource.story.entity.StoryMoneyEntity;
import com.git.hui.boot.multi.datasource.story.mapper.StoryMoneyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * @author yihui
 * @date 20/12/27
 */
@Repository
public class StoryMoneyRepository {
    @Autowired
    private StoryMoneyMapper storyMoneyMapper;

    public void query() {
        List<StoryMoneyEntity> list = storyMoneyMapper.findByIds(Arrays.asList(1, 1000));
        System.out.println(list);


    }
}
