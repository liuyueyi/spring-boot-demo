package com.git.hui.boot.jdbc.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * 不生效的demo用例
 * Created by @author yihui in 16:08 20/1/17.
 */
@Service
public class NotEffectDemo {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        String sql = "replace into money (id, name, money) values" + " (520, '初始化', 200)," + "(530, '初始化', 200)," +
                "(540, '初始化', 200)," + "(550, '初始化', 200)";
        jdbcTemplate.execute(sql);
    }

    /**
     * 私有方法上的注解，不生效
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Transactional
    private boolean testSpecialException(int id) throws Exception {
        if (this.updateName(id)) {
            this.query("after update name", id);
            if (this.update(id)) {
                return true;
            }
        }

        throw new Exception("参数异常");
    }


    /**
     * 非运行异常，且没有通过 rollbackFor 指定抛出的异常，不生效
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Transactional
    public boolean testCompleException(int id) throws Exception {
        if (this.updateName(id)) {
            this.query("after update name", id);
            if (this.update(id)) {
                return true;
            }
        }

        throw new Exception("参数异常");
    }

    public boolean testCall(int id) throws Exception {
        return testCompileException2(id);
    }

    /**
     * 非直接调用，不生效
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean testCompileException2(int id) throws Exception {
        if (this.updateName(id)) {
            this.query("after update name", id);
            if (this.update(id)) {
                return true;
            }
        }

        throw new Exception("参数异常");
    }


    /**
     * 子线程抛异常，主线程无法捕获，导致事务不生效
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean testMultThread(int id) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateName(id);
                query("after update name", id);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean ans = update(id);
                query("after update id", id);
                if (!ans) {
                    throw new RuntimeException("failed to update ans");
                }
            }
        }).start();

        Thread.sleep(1000);
        System.out.println("------- 子线程 --------");

        return true;
    }


    /**
     * 子线程抛异常，主线程无法捕获，导致事务不生效
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean testMultThread2(int id) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateName(id);
                query("after update name", id);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean ans = update(id);
                query("after update id", id);
            }
        }).start();

        Thread.sleep(1000);
        System.out.println("------- 子线程 --------");

        update(id);
        query("after outer update id", id);
        throw new RuntimeException("failed to update ans");
    }

    private boolean updateName(int id) {
        String sql = "update money set `name`='更新' where id=" + id;
        jdbcTemplate.execute(sql);
        return true;
    }

    public void query(String tag, int id) {
        String sql = "select * from money where id=" + id;
        Map map = jdbcTemplate.queryForMap(sql);
        System.out.println(tag + " >>>> " + map);
    }

    private boolean update(int id) {
        String sql = "update money set `money`= `money` + 10 where id=" + id;
        jdbcTemplate.execute(sql);
        return false;
    }
}
