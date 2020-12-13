package com.git.hui.boot.jooq.repository;

import com.git.hui.boot.jooq.h2.tables.PoetryTB;
import com.git.hui.boot.jooq.h2.tables.pojos.PoetryBO;
import com.git.hui.boot.jooq.h2.tables.records.PoetryPO;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 函数查询
 *
 * @author yihui
 * @date 20/12/1
 */
@Repository
public class PoetryFunctionQueryRepository {

    private static final PoetryTB poetryTable = PoetryTB.POETRY;

    @Autowired
    private DSLContext dsl;

    private RecordMapper<PoetryPO, PoetryBO> poetryMapper;

    @PostConstruct
    public void init() {
        // 转换
        poetryMapper = dsl.configuration().recordMapperProvider().provide(poetryTable.recordType(), PoetryBO.class);
    }

    public void test() {
        abs(1);
        sqrt(2);
        pow(2);
        mode(1);
        ceil(2);
        rand(1);
        strLen(2);
        strConcat(2);
        replace(2);
        lowerUpper(2);
        leftRight(2);
        trim(2);
        reverse(2);
        curDate();
        timeAdd();
    }

    public void abs(int id) {
        // 绝对值 select abs(poet_id) from poetry where id = xxx
        Record1<Integer> ans = dsl.select(DSL.abs(poetryTable.POET_ID)).from(poetryTable).where(poetryTable.ID.eq(id)).fetchOne();
        System.out.println(ans.component1());
    }

    public void sqrt(int id) {
        // 开方  select sqrt(poet_id) from poetry where id = xxx
        Record1<BigDecimal> ans = dsl.select(DSL.sqrt(poetryTable.POET_ID)).from(poetryTable).where(poetryTable.ID.eq(id)).fetchOne();
        System.out.println(ans.component1());
    }

    public void pow(int id) {
        // n次方  select pow(poet_id, 2) from poetry where id = xxx
        Record1<BigDecimal> ans = dsl.select(poetryTable.POET_ID.pow(2)).from(poetryTable).where(poetryTable.ID.eq(id)).fetchOne();
        System.out.println(ans.component1());
    }

    public void mode(int id) {
        // 求余数，select mod(poet_id, 2) from poetry where id = xxx
        Record1<Integer> ans = dsl.select(poetryTable.POET_ID.mod(2)).from(poetryTable).where(poetryTable.ID.eq(id)).fetchOne();
        System.out.println(ans.component1());
    }

    public void ceil(int id) {
        // 向上取整，select ceil(poet_id) from poetry where id = xxx
        // floor 向下取整
        // round 四舍五入
        Record1<Integer> ans = dsl.select(DSL.ceil(poetryTable.POET_ID)).from(poetryTable).where(poetryTable.ID.eq(id)).fetchOne();
        System.out.println(ans.component1());
    }

    public void rand(int id) {
        // 随机数 select rand() from poetry where id = xxx
        Record1<BigDecimal> ans = dsl.select(DSL.rand()).from(poetryTable).where(poetryTable.ID.eq(id)).fetchOne();
        System.out.println(ans.component1());
    }

    // 字符串相关

    public void strLen(int id) {
        // 字符串长度  select length(`content`) from poetry where id = xxx
        Record1<Integer> ans = dsl.select(DSL.length(poetryTable.CONTENT)).from(poetryTable).where(poetryTable.ID.eq(id)).fetchOne();
        System.out.println(ans.component1());
    }

    public void strConcat(int id) {
        // 拼接 select concat(title, '--后缀') from poetry where id = xxx
        Record1<String> ans = dsl.select(DSL.concat(poetryTable.TITLE, "--后缀")).from(poetryTable).where(poetryTable.ID.eq(id)).fetchOne();
        System.out.println(ans.component1());
    }

    public void replace(int id) {
        // 替换字符串  select replace(title, '落日', '一灰灰') from poetry where id = xxx
        Record1<String> ans = dsl.select(DSL.replace(poetryTable.TITLE, "落日", "一灰灰")).from(poetryTable).where(poetryTable.ID.eq(id)).fetchOne();
        System.out.println(ans.component1());
    }

    public void lowerUpper(int id) {
        // 转小写  select lower(title) from poetry where id = xxx
        // 转大写  select upper(title) from poetry where id = xxx
        Record1<String> ans = dsl.select(DSL.lower(poetryTable.TITLE)).from(poetryTable).where(poetryTable.ID.eq(id)).fetchOne();
        System.out.println(ans.component1());
    }

    public void leftRight(int id) {
        // 左侧截取n个字符  select left(title, n) from poetry where id = xxx
        // 右侧截取n个字符  select right(title, n) from poetry where id = xxx
        Record1<String> ans = dsl.select(DSL.left(poetryTable.TITLE, 2)).from(poetryTable).where(poetryTable.ID.eq(id)).fetchOne();
        System.out.println(ans.component1());

        ans = dsl.select(DSL.right(poetryTable.TITLE, 2)).from(poetryTable).where(poetryTable.ID.eq(id)).fetchOne();
        System.out.println(ans.component1());
    }

    public void trim(int id) {
        // 去掉两端空格  select rim(title) from poetry where id = xxx
        Record1<String> ans = dsl.select(DSL.trim(poetryTable.TITLE)).from(poetryTable).where(poetryTable.ID.eq(id)).fetchOne();
        System.out.println(ans.component1());
    }

    public void reverse(int id) {
        try {
            // fixme 请注意 h2database 不支持reverse 函数； mysql可以
            // 字符串反转 select reverse(title) from poetry where id = xxx
            Record1<String> ans = dsl.select(DSL.reverse(poetryTable.TITLE)).from(poetryTable).where(poetryTable.ID.eq(id)).fetchOne();
            System.out.println(ans.component1());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // 日期相关

    public void curDate() {
        // fixme h2database 不支持下面的函数；mysql支持
        // 获取当前日期、时间
        // curdate(), current_date() --> 日期，默认 'YYYY-MM-DD' 格式
        // curtime(), current_time() --> 时间，默认 'HH:MM:SS' 格式
        // now(), sysdate() --> 日期时间 YYYY-MM-DD HH:MM:SS
        try {
            Record4<Date, LocalDate, LocalTime, LocalDateTime> ans = dsl.select(DSL.currentDate(), DSL.currentLocalDate(), DSL.currentLocalTime(), DSL.currentLocalDateTime()).fetchOne();
            System.out.println(ans);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void timeAdd() {
        // 日期运算，添加一天
        Record1<Timestamp> ans = dsl.select(DSL.timestampAdd(poetryTable.CREATE_AT, 1, DatePart.DAY)).from(poetryTable).limit(1).fetchOne();
        System.out.println(ans.component1());
    }
}
