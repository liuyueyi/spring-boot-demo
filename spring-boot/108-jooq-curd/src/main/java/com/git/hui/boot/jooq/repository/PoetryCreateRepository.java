package com.git.hui.boot.jooq.repository;

import com.alibaba.fastjson.JSON;
import com.git.hui.boot.jooq.h2.tables.PoetTB;
import com.git.hui.boot.jooq.h2.tables.pojos.PoetBO;
import com.git.hui.boot.jooq.h2.tables.records.PoetPO;
import org.jooq.DSLContext;
import org.jooq.InsertQuery;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 新增record
 * Created by @author yihui in 20:46 20/9/15.
 */
@Repository
public class PoetryCreateRepository {
    private static final PoetTB table = PoetTB.POET;
    @Autowired
    private DSLContext dsl;


    /**
     * 新增记录
     *
     * @param id
     * @param name
     * @return
     */
    public boolean save(int id, String name) {
        PoetPO record = dsl.newRecord(table);
        record.setId(id);
        record.setName(name);
        return record.insert() > 0;
    }

    public boolean save2(int id, String name) {
        return dsl.insertInto(table).set(table.ID, id).set(table.NAME, name).execute() > 0;
    }

    /**
     * 不使用自动生成的代码来原生插入数据
     *
     * @param id
     * @param name
     * @return
     */
    public boolean save3(int id, String name) {
        InsertQuery insertQuery = dsl.insertQuery(DSL.table("poet"));
        insertQuery.addValue(DSL.field("id", Integer.class), id);
        insertQuery.addValue(DSL.field("name", String.class), name);
        return insertQuery.execute() > 0;
    }

    /**
     * 批量添加
     *
     * @param list
     * @return
     */
    public boolean batchSave(List<PoetBO> list) {
        List<PoetPO> poList = list.stream().map(this::bo2po).collect(Collectors.toList());

        int[] ans = dsl.batchInsert(poList).execute();
        System.out.println(JSON.toJSONString(ans));
        return true;
    }

    public boolean batchSave2(List<PoetBO> list) {
        List<PoetPO> poList = list.stream().map(this::bo2po).collect(Collectors.toList());
        return dsl.insertInto(table).values(poList).execute() > 0;
    }

    public boolean batchSave3(List<PoetBO> list) {
        InsertQuery insertQuery = dsl.insertQuery(DSL.table("poet"));
        for (PoetBO bo : list) {
            insertQuery.addValue(DSL.field("id", Integer.class), bo.getId());
            insertQuery.addValue(DSL.field("name", String.class), bo.getName());
            insertQuery.newRecord();
        }

        return insertQuery.execute() > 0;
    }

    private PoetPO bo2po(PoetBO bo) {
        PoetPO po = dsl.newRecord(table);
        po.setId(bo.getId());
        po.setName(bo.getName());
        return po;
    }
}
