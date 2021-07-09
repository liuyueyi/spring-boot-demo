package com.git.hui.boot.mybatis.mapper;

import com.git.hui.boot.mybatis.entity.MoneyPo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * Created by @author yihui in 15:02 19/12/25.
 */
// 注意，如果不使用 @Mapper，则要么使用 @MapperScan 来指定Mapper包路径；要么使用MapperScannerConfigurer来注册
@Mapper
public interface MoneyMapper {

    // 支持主键写回到po

    @Options(useGeneratedKeys = true, keyProperty = "po.id", keyColumn = "id")
    @Insert("insert into money (name, money, is_deleted) values (#{po.name}, #{po.money}, #{po.isDeleted})")
    int savePo(@Param("po") MoneyPo po);

    @Select("select * from money where name=#{name}")
//    @Results(id = "moneyPo", value = {
//    @Result(property = "id", column = "id", id = true, jdbcType = JdbcType.INTEGER),
//            @Result(property = "name", column = "name", jdbcType = JdbcType.VARCHAR),
//            @Result(property = "money", column = "money", jdbcType = JdbcType.INTEGER),
//            @Result(property = "isDeleted", column = "is_deleted", jdbcType = JdbcType.TINYINT),
//            @Result(property = "createAt", column = "create_at", jdbcType = JdbcType.TIMESTAMP),
//            @Result(property = "updateAt", column = "update_at", jdbcType = JdbcType.TIMESTAMP)})
    @ResultMap("money")
    List<MoneyPo> findByName(@Param("name") String name);

    @Update("update money set money=money+#{money} where id=#{id}")
    int addMoney(@Param("id") int id, @Param("money") int money);

    @Delete("delete from money where id = #{id,jdbcType=INTEGER}")
    int delPo(@Param("id") int id);

    @Select("<script> select * from money " +
            "<trim prefix=\"WHERE\" prefixOverrides=\"AND | OR\">" +
            "   <if test=\"id != null\">" +
            "       id = #{id}" +
            "   </if>" +
            "   <if test=\"name != null\">" +
            "       AND name=#{name}" +
            "   </if>" +
            "   <if test=\"money != null\">" +
            "       AND money=#{money}" +
            "   </if>" +
            "</trim>" +
            "</script>")
    // 注意这里面的ID, 其他的地方可以借助 @ResultMap 来实现等同的转换
    @Results(id = "money", value = {@Result(property = "id", column = "id", id = true, jdbcType = JdbcType.INTEGER),
            @Result(property = "name", column = "name", jdbcType = JdbcType.VARCHAR),
            @Result(property = "money", column = "money", jdbcType = JdbcType.INTEGER),
            @Result(property = "isDeleted", column = "is_deleted", jdbcType = JdbcType.TINYINT),
            @Result(property = "createAt", column = "create_at", jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "updateAt", column = "update_at", jdbcType = JdbcType.TIMESTAMP)})
    List<MoneyPo> findByPo(MoneyPo po);
}
