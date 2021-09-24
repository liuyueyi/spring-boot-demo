package com.git.hui.boot.mybatis.mapper;

import com.git.hui.boot.mybatis.entity.MoneyPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用于测试传入的参数，再mybatis中，最终替换成sql时，类型如何确定的
 * @author yihui
 * @date 2021/8/2
 */
@Mapper
public interface MoneyMapperV3 {
    /**
     * int类型，最终的sql中参数替换的也是int
     * @param name
     * @return
     */
    List<MoneyPo> queryByName(@Param("name") Integer name);

    /**
     * 指定 jdbcType 并不会影响最终的sql中参数替换类型
     * @param name
     * @return
     */
    List<MoneyPo> queryByNameV2(@Param("name") Integer name);

    /**
     * 如果传入的参数类型为string，会自动带上''
     * @param name
     * @return
     */
    List<MoneyPo> queryByNameV3(@Param("name") String name);

    /**
     * 通过自定义的 TypeHandler 来实现 java <-> jdbc 类型的互转，从而实现即时传入的是int/long，也会转成String
     * @param name
     * @return
     */
    List<MoneyPo> queryByNameV4(@Param("name") Integer name);
}
