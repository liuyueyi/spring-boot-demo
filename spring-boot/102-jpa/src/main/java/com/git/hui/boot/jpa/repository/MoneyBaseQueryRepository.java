package com.git.hui.boot.jpa.repository;

import com.git.hui.boot.jpa.entity.MoneyPO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * 基础的查询使用姿势
 *
 * Created by @author yihui in 08:59 19/6/12.
 */
public interface MoneyBaseQueryRepository extends CrudRepository<MoneyPO, Integer> {

    /**
     * 根据用户名查询
     *
     * @param name
     * @return
     */
    List<MoneyPO> findByName(String name);

    List<MoneyPO> queryByName(String name);

    /**
     * 根据用户名 + money查询
     *
     * @param name
     * @param money
     * @return
     */
    List<MoneyPO> findByNameAndMoney(String name, Long money);


    /**
     * 根据用户名 or id查询
     *
     * @param name
     * @param id
     * @return
     */
    List<MoneyPO> findByNameOrId(String name, Integer id);

    /**
     * in查询
     *
     * @param moneys
     * @return
     */
    List<MoneyPO> findByMoneyIn(List<Long> moneys);


    /**
     * like查询
     *
     * @param name
     * @return
     */
    List<MoneyPO> findByNameLike(String name);

    /**
     * 查询大于or等于指定id的所有记录
     *
     * @param id
     * @return
     */
    List<MoneyPO> findByIdGreaterThanEqual(Integer id);

    /**
     * 查询小于or等于指定id的所有记录
     *
     * @param id
     * @return
     */
    List<MoneyPO> findByIdLessThanEqual(Integer id);

    /**
     * between查询
     *
     * @param low
     * @param high
     * @return
     */
    List<MoneyPO> findByIdIsBetween(Integer low, Integer high);


    /**
     * 根据money查询，并将最终的结果根据id进行倒排
     *
     * @param money
     * @return
     */
    List<MoneyPO> findByMoneyOrderByIdDesc(Long money);

    /**
     * 根据多个条件进行排序
     *
     * @param id
     * @return
     */
    List<MoneyPO> queryByIdGreaterThanEqualOrderByMoneyDescIdAsc(Integer id);

    /**
     * 分页查询，获取前面三个数据
     *
     * @param id
     * @return
     */
    List<MoneyPO> findTop3ByIdGreaterThan(Integer id);

    /**
     * 分页查询
     *
     * @param id
     * @param pageable page 从0开始表示查询第0页，即返回size个正好>id数量的数据
     * @return
     */
    List<MoneyPO> findByIdGreaterThan(Integer id, Pageable pageable);
}
