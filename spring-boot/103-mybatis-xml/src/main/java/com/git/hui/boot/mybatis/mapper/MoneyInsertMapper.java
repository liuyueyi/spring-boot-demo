package com.git.hui.boot.mybatis.mapper;

import com.git.hui.boot.mybatis.entity.MoneyPo;
import com.git.hui.boot.mybatis.entity.QueryBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 批量插入的几种方式
 * @author yihui
 * @date 2021/8/2
 */
@Mapper
public interface MoneyInsertMapper {
    /**
     * 批量写入
     * @param list
     * @return
     */
    int batchSave(@Param("list") List<MoneyPo> list);

    /**
     * 写入
     * @param po
     * @return
     */
    int save(@Param("po") MoneyPo po);
}
