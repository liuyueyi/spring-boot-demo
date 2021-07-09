package com.git.hui.boot.mybatis.service;

import com.git.hui.boot.mybatis.entity.MoneyPo;
import com.git.hui.boot.mybatis.mapper.MoneyMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author yihui
 * @date 2021/7/6
 */
@Slf4j
@Service
public class MoneyService {
    @Autowired
    private MoneyMapper moneyMapper;

    public void basicTest() {
        int id = save();
        log.info("save {}", getById(id));
        boolean update = update(id, 202L);
        log.info("update {}, {}", update, getById(id));
        boolean delete = delete(id);
        log.info("delete {}, {}", delete, getById(id));
    }

    private int save() {
        MoneyPo po = new MoneyPo();
        po.setName("一灰灰blog");
        po.setMoney(101L);
        po.setIsDeleted(0);
        moneyMapper.save(po);
        return po.getId();
    }

    private boolean update(int id, long newMoney) {
        int ans = moneyMapper.update(id, newMoney);
        return ans > 0;
    }

    private boolean delete(int id) {
        return moneyMapper.delete(id) > 0;
    }

    private MoneyPo getById(int id) {
        return moneyMapper.getById(id);
    }

    // ------------------ 基础使用分隔符

    public void testProvider() {
        int id = save();
        log.info("v1: {}", moneyMapper.getByIdForProvider(id));
        log.info("v2: {}", moneyMapper.getByIdForProviderV2(id));
        log.info("v3: {}", moneyMapper.getByIdForProviderV3(id));
        delete(id);
    }

    /**
     * sql模板, 用于 @xxxProvider
     * - 除了下面这个无参数的模板方式，还可以使用下面的几种传参方式
     * - 直接作为传参:  getByIdSql(int id)
     * - 使用Map传参:  getByIdSql(Map<String, Object> params)
     * -- 通过 params.get(xxx) 获取参数值
     *
     * @return
     */
    public String getByIdSql() {
        // 拼装sql
        // 下面等同于 select `id`, `money`, `is_deleted`, `create_at`, `update_at` from money where id = #{id} limit 1
        return new SQL()
                .SELECT("id", "money", "is_deleted", "create_at", "update_at")
                .FROM("money")
                .WHERE("id=#{id}")
                .LIMIT(1).toString();
    }

    public String getByIdSqlV2(int id) {
        // 拼装sql
        // 下面等同于 select `id`, `money`, `is_deleted`, `create_at`, `update_at` from money where id = #{id} limit 1
        return new SQL()
                .SELECT("id", "money", "is_deleted", "create_at", "update_at")
                .FROM("money")
                .WHERE("id=" + id)
                .LIMIT(1).toString();
    }

    public String getByIdSqlV3(Map<String, Object> params) {
        // 拼装sql
        // 下面等同于 select `id`, `money`, `is_deleted`, `create_at`, `update_at` from money where id = #{id} limit 1
        return new SQL()
                .SELECT("id", "money", "is_deleted", "create_at", "update_at")
                .FROM("money")
                .WHERE("id=" + params.get("id"))
                .LIMIT(1).toString();
    }


}
