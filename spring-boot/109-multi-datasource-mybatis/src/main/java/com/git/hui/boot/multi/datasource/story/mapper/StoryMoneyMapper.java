package com.git.hui.boot.multi.datasource.story.mapper;

import com.git.hui.boot.multi.datasource.story.entity.StoryMoneyEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yihui
 * @date 20/12/27
 */
@Mapper
public interface StoryMoneyMapper {
    List<StoryMoneyEntity> findByIds(List<Integer> ids);
}
