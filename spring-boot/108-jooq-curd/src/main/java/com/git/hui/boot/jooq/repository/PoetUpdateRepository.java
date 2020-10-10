package com.git.hui.boot.jooq.repository;

import com.alibaba.fastjson.JSONObject;
import com.git.hui.boot.jooq.h2.tables.PoetTB;
import com.git.hui.boot.jooq.h2.tables.pojos.PoetBO;
import com.git.hui.boot.jooq.h2.tables.records.PoetPO;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.jooq.UpdateQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by @author yihui in 10:52 20/9/16.
 */
@Repository
public class PoetUpdateRepository {
    private static final PoetTB table = PoetTB.POET;

    @Autowired
    private DSLContext dsl;

    public void test() {
        this.updateName(11, "name1");
        this.updateName2(12, "name2");
        this.updateName3(13, "name3");
        this.updateName4(14, "name4");
        this.updateName5(15, "name5");

        this.batchUpdate(Arrays.asList(new PoetBO(16, "name6"), new PoetBO(17, "name7")));

        RecordMapper<PoetPO, PoetBO> mapper =
                dsl.configuration().recordMapperProvider().provide(table.recordType(), PoetBO.class);
        List<PoetBO> result = dsl.selectFrom(table).fetch().map(mapper);
        System.out.println(result);
    }

    /**
     * 最基本的数据更新
     *
     * @param id
     * @param name
     * @return
     */
    private boolean updateName(int id, String name) {
        // ==> update poet set `name`=xx where id=xxx
        return dsl.update(table).set(table.NAME, name).where(table.ID.eq(id)).execute() > 0;
    }

    private boolean updateName2(int id, String name) {
        // concat 修改
        // 等同于 ==> update poet set `name`=concat(`name`, xxx) where id=xxx
        return dsl.update(table).set(table.NAME, table.NAME.concat(name)).where(table.ID.eq(id)).execute() > 0;
    }


    private boolean updateName3(int id, String name) {
        // update query方式
        UpdateQuery updateQuery = dsl.updateQuery(table);
        updateQuery.addValue(table.NAME, name);
        updateQuery.addConditions(table.ID.eq(id));
        return updateQuery.execute() > 0;
    }

    /**
     * 使用Entity进行更新
     *
     * @param id
     * @param name
     * @return
     */
    private boolean updateName4(int id, String name) {
        PoetPO poetPO = dsl.newRecord(table);
        poetPO.setId(id);
        poetPO.setName(name);
        return poetPO.update() > 0;
    }

    private boolean updateName5(int id, String name) {
        PoetPO po = new PoetPO();
        po.setName(name);
        return dsl.executeUpdate(po, table.ID.eq(id)) > 0;
    }


    /**
     * 批量更新
     *
     * @param list
     * @return
     */
    private boolean batchUpdate(List<PoetBO> list) {
        List<PoetPO> poList = list.stream().map(this::bo2po).collect(Collectors.toList());
        int[] ans = dsl.batchUpdate(poList).execute();
        System.out.println(JSONObject.toJSONString(ans));
        return true;
    }

    private PoetPO bo2po(PoetBO bo) {
        PoetPO po = dsl.newRecord(table);
        po.setId(bo.getId());
        po.setName(bo.getName());
        return po;
    }
}
