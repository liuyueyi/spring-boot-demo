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

    /**
     * 对于groupBy + 字段名这种场景，需要注意，只能使用 ${}，而不能使用 #{}
     * @param name
     * @return
     */
    List<MoneyPo> groupBy(@Param("tt") String name);

    /**
     * 枚举字段的查询注意事项，不要传入数字，否则容易掉坑
     *
     * @param bank
     * @return
     */
    List<MoneyPo> queryByBank(@Param("bank") Integer bank);
}
