package com.git.hui.boot.db.mapper;

import com.git.hui.boot.db.entity.MoneyPo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author YiHui
 * @date 2023/7/18
 */
@Mapper
public interface MoneyMapper {

    /**
     * 保存数据，并保存主键id
     *
     * @param po
     * @return int
     */
    @Options(useGeneratedKeys = true, keyProperty = "po.id", keyColumn = "id")
    @Insert("insert into money (name, money, is_deleted) values (#{po.name}, #{po.money}, #{po.isDeleted})")
    int save(@Param("po") MoneyPo po);

    /**
     * 更新
     *
     * @param id    id
     * @param money 钱
     * @return int
     */
    @Update("update money set `money`=#{money} where id = #{id}")
    int update(@Param("id") int id, @Param("money") long money);

    /**
     * 删除数据
     *
     * @param id id
     * @return int
     */
    @Delete("delete from money where id = #{id}")
    int delete(@Param("id") int id);

    /**
     * 主键查询
     *
     * @param id id
     * @return {@link MoneyPo}
     */
    @Select("select * from money where id = #{id}")
    @Results(id = "moneyResultMap", value = {
            @Result(property = "id", column = "id", id = true, jdbcType = JdbcType.INTEGER),
            @Result(property = "name", column = "name", jdbcType = JdbcType.VARCHAR),
            @Result(property = "money", column = "money", jdbcType = JdbcType.INTEGER),
            @Result(property = "isDeleted", column = "is_deleted", jdbcType = JdbcType.TINYINT),
            @Result(property = "createAt", column = "create_at", jdbcType = JdbcType.TIMESTAMP),
            @Result(property = "updateAt", column = "update_at", jdbcType = JdbcType.TIMESTAMP)})
    MoneyPo getById(@Param("id") int id);

    @Select("select * from money where `name` = #{name}")
    @ResultMap(value = "moneyResultMap")
    MoneyPo getByName(@Param("name") String name);

    /**
     * foreach 查询
     *
     * @param ids
     * @return
     */
    @Select("<script> select * from money where id in  " +
            "<foreach collection='ids' index='index' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach></script>")
    List<MoneyPo> getByIds(@Param("ids") List<Integer> ids);
}
