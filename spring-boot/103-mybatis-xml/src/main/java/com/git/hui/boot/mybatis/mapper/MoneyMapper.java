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

    int batchSave(@Param("list") List<MoneyPo> list);

    int savePo(@Param("po") MoneyPo po);

    /**
     * 请注意返回值不能是 long， 否则当记录不存在时，会抛绑定异常
     * @param id
     * @return
     */
    Long findIdById(@Param("id") long id);

    List<MoneyPo> findByName(@Param("name") String name);

    int addMoney(@Param("id") int id, @Param("money") int money);

    int delPo(@Param("id") int id);

    /**
     * 单个参数时，默认可以直接通过参数名来表示，实际上#{}中用任意一个值都可以，没有任何限制，都表示的是这个唯一的参数
     * @param id
     * @return
     */
    MoneyPo findById(int id);

    /**
     * 演示xml中的 #{} 为一个匹配补上的字符串，也可以正确的实现参数替换
     * @param id
     * @return
     */
    MoneyPo findByIdV2(int id);

    /**
     * 不指定参数名时，mybatis自动封装一个  param1 ... paramN的Map，其中n表示第n个参数
     * 也可以使用 arg0...n 来指代具体的参数
     *
     * @param name
     * @param money
     * @return
     */
    List<MoneyPo> findByNameAndMoney(String name, Integer money);

    /**
     * 参数类型为map时，直接使用key即可
     * @param map
     * @return
     */
    List<MoneyPo> findByMap(Map<String, Object> map);

    /**
     * 参数类型为java对象，同样直接使用field name即可
     * @param po
     * @return
     */
    List<MoneyPo> findByPo(MoneyPo po);

    /**
     * 位查询
     * @param bit
     * @return
     */
    List<Long> queryBitCondition(int bit);
}
