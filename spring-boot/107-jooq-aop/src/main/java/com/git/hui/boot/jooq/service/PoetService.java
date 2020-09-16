package com.git.hui.boot.jooq.service;

import com.git.hui.boot.jooq.h2.tables.Poet;
import com.git.hui.boot.jooq.h2.tables.records.PoetRecord;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.git.hui.boot.jooq.h2.tables.Poet.POET;

/**
 * Created by @author yihui in 08:19 20/9/9.
 */
@Service
public class PoetService {

    @Autowired
    DSLContext dsl;

    public int create(int id, String author) {
        return dsl.insertInto(POET).set(POET.ID, id).set(POET.NAME, author).execute();
    }

    public PoetRecord get(int id) {
        PoetRecord record = dsl.selectFrom(POET).where(POET.ID.eq(id)).fetchOne();


        return record;
    }

    public Poet getById(int id) {
        PoetRecord record = dsl.selectFrom(POET).where(POET.ID.eq(id)).fetchOne();
        RecordMapper<PoetRecord, Poet> mapper =
                dsl.configuration().recordMapperProvider().provide(POET.recordType(), POET.getClass());
        return mapper.map(record);
    }

    public int update(int id, String author) {
        return dsl.update(POET).set(POET.NAME, author).where(POET.ID.eq(id)).execute();
    }

    public int delete(int id) {
        return dsl.delete(POET).where(POET.ID.eq(id)).execute();
    }

    public List<PoetRecord> getAll() {
        return dsl.selectFrom(POET).fetch();
    }
}