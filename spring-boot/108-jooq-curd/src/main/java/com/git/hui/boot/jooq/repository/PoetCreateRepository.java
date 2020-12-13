package com.git.hui.boot.jooq.repository;

import com.alibaba.fastjson.JSON;
import com.git.hui.boot.jooq.h2.tables.PoetTB;
import com.git.hui.boot.jooq.h2.tables.pojos.PoetBO;
import com.git.hui.boot.jooq.h2.tables.records.PoetPO;
import org.jooq.DSLContext;
import org.jooq.InsertQuery;
import org.jooq.InsertValuesStep2;
import org.jooq.RecordMapper;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 新增record
 * Created by @author yihui in 20:46 20/9/15.
 */
@Repository
public class PoetCreateRepository {
    private static final PoetTB table = PoetTB.POET;
    @Autowired
    private DSLContext dsl;


    public void test() {
        this.save(11, "一灰");
        this.save2(12, "一灰灰");
        this.save3(13, "一灰灰Blog");


        this.batchSave(Arrays.asList(new PoetBO(14, "yh"), new PoetBO(15, "yhh")));
        this.batchSave2(Arrays.asList(new PoetBO(16, "yihui"), new PoetBO(17, "yihuihui")));
        this.batchSave3(Arrays.asList(new PoetBO(18, "YiHui"), new PoetBO(19, "YiHuiBlog")));

        RecordMapper<PoetPO, PoetBO> mapper =
                dsl.configuration().recordMapperProvider().provide(table.recordType(), PoetBO.class);
        List<PoetBO> result = dsl.selectFrom(table).fetch().map(mapper);
        System.out.println(result);
    }

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
        // 当不使用自动生成的对象时，table可以用 DSL.table()指定，列可以用 DSL.field()指定
        InsertQuery insertQuery = dsl.insertQuery(DSL.table("poet"));
        insertQuery.addValue(DSL.field("id", Integer.class), id);
        insertQuery.addValue(DSL.field("name", String.class), name);
        return insertQuery.execute() > 0;
    }

    /**
     * 通过Record执行批量添加
     * <p>
     * 通过源码查看，这种插入方式实际上是单条单条的写入数据，和下面的一次插入多条有本质区别
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

    /**
     * 类sql写法，批量添加
     *
     * @param list
     * @return
     */
    public boolean batchSave2(List<PoetBO> list) {
        InsertValuesStep2<PoetPO, Integer, String> step = dsl.insertInto(table).columns(table.ID, table.NAME);
        for (PoetBO bo : list) {
            step.values(bo.getId(), bo.getName());
        }
        return step.execute() > 0;
    }

    /**
     * 不基于自动生成的代码，来批量添加数据
     *
     * @param list
     * @return
     */
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
