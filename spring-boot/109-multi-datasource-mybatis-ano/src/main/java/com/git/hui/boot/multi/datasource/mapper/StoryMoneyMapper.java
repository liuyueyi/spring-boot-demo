package com.git.hui.boot.multi.datasource.mapper;

import com.git.hui.boot.multi.datasource.dynamic.ano.DS;
import com.git.hui.boot.multi.datasource.entity.StoryMoneyEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yihui
 * @date 20/12/27
 */
@DS(value = "story")
@Mapper
public interface StoryMoneyMapper {
    List<StoryMoneyEntity> findByIds(List<Integer> ids);
}
