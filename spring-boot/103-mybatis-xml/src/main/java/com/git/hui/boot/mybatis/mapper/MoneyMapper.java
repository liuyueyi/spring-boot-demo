package com.git.hui.boot.mybatis.mapper;

import com.git.hui.boot.mybatis.entity.MoneyPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by @author yihui in 15:02 19/12/25.
 */
@Mapper
public interface MoneyMapper {

    int savePo(@Param("po") MoneyPo po);

    List<MoneyPo> findByName(@Param("name") String name);

    int addMoney(@Param("id") int id, @Param("money") int money);

    int delPo(@Param("id") int id);

    MoneyPo findById(int id);

    List<MoneyPo> findByNameAndMoney(String name, Integer money);

    List<MoneyPo> findByMap(Map<String, Object> map);

    List<MoneyPo> findByPo(MoneyPo po);
}
