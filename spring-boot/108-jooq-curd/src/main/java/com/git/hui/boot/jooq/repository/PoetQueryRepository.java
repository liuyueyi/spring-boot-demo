package com.git.hui.boot.jooq.repository;

import com.git.hui.boot.jooq.h2.tables.PoetTB;
import com.git.hui.boot.jooq.h2.tables.PoetryTB;
import com.git.hui.boot.jooq.h2.tables.pojos.PoetBO;
import com.git.hui.boot.jooq.h2.tables.pojos.PoetryBO;
import com.git.hui.boot.jooq.h2.tables.records.PoetPO;
import com.git.hui.boot.jooq.h2.tables.records.PoetryPO;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * 查询实例demo
 * Created by @author yihui in 10:15 20/9/14.
 */
@Repository
public class PoetQueryRepository {
    private static final PoetTB poetTable = PoetTB.POET;
    private static final PoetryTB poetryTable = PoetryTB.POETRY;

    @Autowired
    private DSLContext dsl;

    private RecordMapper<PoetPO, PoetBO> poetMapper;
    private RecordMapper<PoetryPO, PoetryBO> poetryMapper;

    @PostConstruct
    public void init() {
        // 转换
        poetMapper = dsl.configuration().recordMapperProvider().provide(poetTable.recordType(), PoetBO.class);
        poetryMapper = dsl.configuration().recordMapperProvider().provide(poetryTable.recordType(), PoetryBO.class);
    }

    public void test() {
        System.out.println(queryById(1));
        System.out.println(queryFieldsById(1));
        System.out.println(queryFieldsById2(1));

        System.out.println(queryByNotEq(1));
        System.out.println(queryByIdGT(1));
        System.out.println(queryByIdGE(1));
        System.out.println(queryByIdLT(2));
        System.out.println(queryByIdIn(Arrays.asList(1, 2, 3)));
        System.out.println(queryByIdNotIn(Arrays.asList(1, 2, 3)));
        System.out.println(queryByIdBetween(1, 4));
        System.out.println(queryByNameLike("白"));
        System.out.println(queryByNameIsNull());
        System.out.println(queryByIdAndName(1, "李白"));
        System.out.println(queryByIdOrName(1, "一灰"));
        System.out.println(queryByIdGtOrderByIdDesc(1));
        System.out.println(queryByIdGtOrderByPoetIdAndId(1));
        System.out.println(queryLimit(2));
        System.out.println(queryLimit(1, 2));
        System.out.println(queryOffset(1, 2));
    }

    /**
     * 主键查询
     *
     * @param id
     * @return
     */
    public PoetBO queryById(int id) {
        // select * from poet where id = xxx
        return poetMapper.map(dsl.selectFrom(poetTable).where(poetTable.ID.eq(id)).fetchOne());
    }

    /**
     * 指定字段查询
     *
     * @param id
     * @return
     */
    public String queryFieldsById(int id) {
        // select `name` from poet where id = xxx
        Record1<String> record = dsl.select(poetTable.NAME).from(poetTable).where(poetTable.ID.eq(id)).fetchOne();
        return record.get(poetTable.NAME);
    }

    /**
     * 列别名
     *
     * @param id
     * @return
     */
    public String queryFieldsById2(int id) {
        // select `name` as author from poet where id = xxx
        Record1<String> record =
                dsl.select(poetTable.NAME.as("author")).from(poetTable).where(poetTable.ID.eq(id)).fetchOne();
        return (String) record.get("author");
    }


    // ========================== 条件查询 ===============================

    /**
     * 条件比较
     *
     * @param id
     * @return
     */
    public List<PoetBO> queryByNotEq(int id) {
        // select * from poet where id != xxx
        return dsl.selectFrom(poetTable).where(poetTable.ID.notEqual(id)).fetch().map(poetMapper);
    }

    public List<PoetBO> queryByIdGT(int id) {
        // select * from poet where id > xxx
        return dsl.selectFrom(poetTable).where(poetTable.ID.gt(id)).fetch().map(poetMapper);
    }

    public List<PoetBO> queryByIdGE(int id) {
        // select * from poet where id >= xxx
        return dsl.selectFrom(poetTable).where(poetTable.ID.ge(id)).fetch().map(poetMapper);
    }

    public List<PoetBO> queryByIdLT(int id) {
        // select * from poet where id < xxx
        return dsl.selectFrom(poetTable).where(poetTable.ID.lt(id)).fetch().map(poetMapper);
    }

    public List<PoetBO> queryByIdIn(List<Integer> ids) {
        // select * from poet where id in (xxx)
        return dsl.selectFrom(poetTable).where(poetTable.ID.in(ids)).fetch().map(poetMapper);
    }

    public List<PoetBO> queryByIdNotIn(List<Integer> ids) {
        // select * from poet where id not in (xxx)
        return dsl.selectFrom(poetTable).where(poetTable.ID.notIn(ids)).fetch().map(poetMapper);
    }

    public List<PoetBO> queryByIdBetween(int left, int right) {
        // select * from poet where id between a and b
        return dsl.selectFrom(poetTable).where(poetTable.ID.between(left, right)).fetch(poetMapper);
    }

    public List<PoetBO> queryByNameLike(String name) {
        // select * from poet where name like '%xxx%'
        return dsl.selectFrom(poetTable).where(poetTable.NAME.like("%" + name + " %")).fetch(poetMapper);
    }

    public List<PoetBO> queryByNameIsNull() {
        // select * from poet where name is null;
        return dsl.selectFrom(poetTable).where(poetTable.NAME.isNull()).fetch(poetMapper);
    }

    public PoetBO queryByIdAndName(int id, String name) {
        // select * from poet where name = xxx and id = xxx
        return dsl.selectFrom(poetTable).where(poetTable.ID.eq(id)).and(poetTable.NAME.eq(name)).fetchOne(poetMapper);
    }

    public List<PoetBO> queryByIdOrName(int id, String name) {
        // select * from poet where name = xxx or id = xxx
        return dsl.selectFrom(poetTable).where(poetTable.ID.eq(id)).or(poetTable.NAME.eq(name)).fetch(poetMapper);
    }


    // ========================== 排序 ===============================

    public List<PoetryBO> queryByIdGtOrderByIdDesc(int id) {
        return dsl.selectFrom(poetryTable).where(poetryTable.ID.gt(id)).orderBy(poetryTable.ID.desc())
                .fetch(poetryMapper);
    }

    public List<PoetryBO> queryByIdGtOrderByPoetIdAndId(int id) {
        // 双字段的排序
        // select * from poetry where id > xxx order by poet_id asc, id asc
        return dsl.selectFrom(poetryTable).where(poetryTable.ID.gt(id))
                .orderBy(poetryTable.POET_ID.asc(), poetryTable.ID.asc()).fetch(poetryMapper);
    }


    // ----------------- 分页 --------------

    public List<PoetryBO> queryLimit(int limit) {
        // select * from poetry limit xxx
        return dsl.selectFrom(poetryTable).limit(limit).fetch(poetryMapper);
    }

    public List<PoetryBO> queryLimit(int offset, int limit) {
        // select * from poetry limit xxx, xxx
        return dsl.selectFrom(poetryTable).limit(offset, limit).fetch(poetryMapper);
    }

    public List<PoetryBO> queryOffset(int offset, int limit) {
        // select * from poetry limit xxx, xxx
        return dsl.selectFrom(poetryTable).offset(offset).limit(limit).fetch(poetryMapper);
    }

}
