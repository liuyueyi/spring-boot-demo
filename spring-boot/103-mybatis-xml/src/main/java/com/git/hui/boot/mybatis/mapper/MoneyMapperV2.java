package com.git.hui.boot.mybatis.mapper;

import com.git.hui.boot.mybatis.entity.MoneyPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author yihui
 * @date 2021/8/2
 */
@Mapper
public interface MoneyMapperV2 {

    List<MoneyPo> queryByCondition(Map<String, Object> params);

    List<MoneyPo> queryByConditionV2(Map<String, Object> params);

    /**
     * choose 标签，相当于 switch
     *
     * @param params
     * @return
     */
    List<MoneyPo> queryByConditionV3(Map<String, Object> params);

    /**
     * 主键in查询
     *
     * @param ids
     * @return
     */
    List<MoneyPo> queryByIds(@Param("ids") List<Integer> ids);

    /**
     * 多字段in查询
     *
     * @param items
     * @return
     */
    List<MoneyPo> queryByIdAndNames(@Param("items") List<Map<String, Object>> items);

    /**
     * 更新
     *
     * @param params
     * @return
     */
    int update(Map<String, Object> params);
}
