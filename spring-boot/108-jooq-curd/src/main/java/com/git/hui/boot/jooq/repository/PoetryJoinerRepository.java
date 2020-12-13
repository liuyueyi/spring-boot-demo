package com.git.hui.boot.jooq.repository;

import com.git.hui.boot.jooq.h2.tables.PoetTB;
import com.git.hui.boot.jooq.h2.tables.PoetryTB;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 函数查询
 *
 * @author yihui
 * @date 20/12/1
 */
@Repository
public class PoetryJoinerRepository {
    private static final PoetTB poetTable = PoetTB.POET;
    private static final PoetryTB poetryTable = PoetryTB.POETRY;

    @Autowired
    private DSLContext dsl;

    public void test() {
        innerJoin(1);
        leftJoin(10);
        leftJoin(99);
        rightJoin(10);
        rightJoin(99);
        union();
    }

    public void innerJoin(int poetId) {
        // inner join 内连接: select * from poet inner join poetry on poet.id=poetry.poet_id where poet.id=xxx
        Result<Record> record = dsl.selectFrom(poetTable.innerJoin(poetryTable).on(poetTable.ID.eq(poetryTable.POET_ID))).where(poetTable.ID.eq(poetId)).fetch();
        System.out.println(">>>>>>>>> inner join <<<<<<<<<<");
        System.out.println(record);

        // inner join 比较常用的写法 select `name`, `title`, `content` from poet, poetry where poet.id=poetry.poet_id and poet.id = xxx
        Result<Record3<String, String, String>> res = dsl.select(poetTable.NAME, poetryTable.TITLE, poetryTable.CONTENT).from(poetTable, poetryTable).where(poetTable.ID.eq(poetryTable.POET_ID)).and(poetTable.ID.eq(poetId)).fetch();
        System.out.println(res);
    }

    public void leftJoin(int poetId) {
        // left join, 以左表为主，右表显示与左表有交集的数据，若不存在，使用null填充（若左表不存在，右表有数据，则不展示）
        // select * from poet left join poetry on poet.id=poetry.poet_id where poet.id=xxx
        Result<Record> record = dsl.selectFrom(poetTable.leftJoin(poetryTable).on(poetTable.ID.eq(poetryTable.POET_ID))).where(poetTable.ID.eq(poetId)).fetch();
        System.out.println(">>>>>>>>> left join <<<<<<<<<<");
        System.out.println(record);
    }

    public void rightJoin(int poetId) {
        // right join, 以右表为主，左表显示与右表有交集的数据，若不存在，使用null填充（若右表不存在数据，左表有数据，则不展示）
        // select * from poet right join poetry on poet.id=poetry.poet_id where poetry.poet_id=xxx
        Result<Record> record = dsl.selectFrom(poetTable.rightJoin(poetryTable).on(poetTable.ID.eq(poetryTable.POET_ID))).where(poetryTable.POET_ID.eq(poetId)).fetch();
        System.out.println(">>>>>>>>> right join <<<<<<<<<<");
        System.out.println(record);
    }

    public void union() {
        // union 联合，特点是会去重重复的数据
        // select id from poet union select poet_id from poetry
        Result<Record1<Integer>> res = dsl.select(poetTable.ID).from(poetTable).union(dsl.select(poetryTable.POET_ID).from(poetryTable)).fetch();
        System.out.println(">>>>>>>>> union <<<<<<<<<<");
        System.out.println(res);

        System.out.println(">>>>>>>>> union all <<<<<<<<<<");
        res = dsl.select(poetTable.ID).from(poetTable).unionAll(dsl.select(poetryTable.POET_ID).from(poetryTable)).fetch();
        System.out.println(res);
    }
}
