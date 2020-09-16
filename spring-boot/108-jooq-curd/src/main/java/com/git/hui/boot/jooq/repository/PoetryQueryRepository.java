package com.git.hui.boot.jooq.repository;

import com.git.hui.boot.jooq.h2.tables.PoetTB;
import com.git.hui.boot.jooq.h2.tables.pojos.PoetBO;
import com.git.hui.boot.jooq.h2.tables.records.PoetPO;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 查询实例demo
 * Created by @author yihui in 10:15 20/9/14.
 */
@Repository
public class PoetryQueryRepository {
    private static final PoetTB table = PoetTB.POET;

    @Autowired
    private DSLContext dsl;

    private RecordMapper<PoetPO, PoetBO> mapper;

    public PoetryQueryRepository() {
        // 转换
        mapper = dsl.configuration().recordMapperProvider().provide(table.recordType(), PoetBO.class);
    }

    /**
     * 主键查询
     *
     * @param id
     * @return
     */
    public PoetBO queryById(int id) {
        return mapper.map(dsl.selectFrom(table).where(table.ID.eq(id)).fetchOne());
    }


}
