package com.git.hui.boot.jdbc.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * 事务传递 示例demo
 * Created by @author yihui in 10:59 20/2/2.
 */
@Component
public class PropagationDemo {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        String sql = "replace into money (id, name, money) values (420, '初始化', 200)," + "(430, '初始化', 200)," +
                "(440, '初始化', 200)," + "(450, '初始化', 200)," + "(460, '初始化', 200)," + "(470, '初始化', 200)," +
                "(480, '初始化', 200)," + "(490, '初始化', 200)";
        jdbcTemplate.execute(sql);
    }


    /**
     * 如果存在一个事务，则支持当前事务。如果没有事务则开启一个新的事务
     *
     * @param id
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void required(int id) throws Exception {
        if (this.updateName(id)) {
            this.query("required: after updateMoney name", id);
            if (this.updateMoney(id)) {
                return;
            }
        }

        throw new Exception("事务回滚!!!");
    }


    /**
     * 如果存在一个事务，支持当前事务。如果没有事务，则非事务的执行
     *
     * 简单来讲，外部直接调用这个方法时，非事务方式执行；通过supportTransaction 这个事务方法内部调用support方法时，以事务方式执行
     *
     * @param id
     * @throws Exception
     */
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public void support(int id) throws Exception {
        if (this.updateName(id)) {
            this.query("support: after updateMoney name", id);
            if (this.updateMoney(id)) {
                return;
            }
        }

        throw new Exception("事务回滚!!!");
    }




    /**
     * 需要在一个正常的事务内执行，否则抛异常
     *
     * @param id
     * @throws Exception
     */
    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
    public void mandatory(int id) throws Exception {
        if (this.updateName(id)) {
            this.query("mandatory: after updateMoney name", id);
            if (this.updateMoney(id)) {
                return;
            }
        }

        throw new Exception("事务回滚!!!");
    }


    /**
     * 总是非事务地执行，并挂起任何存在的事务
     *
     * @param id
     * @throws Exception
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public void notSupport(int id) throws Exception {
        if (this.updateName(id)) {
            this.query("notSupport: after updateMoney name", id);
            if (this.updateMoney(id)) {
                return;
            }
        }
        throw new Exception("回滚!");
    }

    /**
     * 总是非事务地执行，如果存在一个活动事务，则抛出异常。
     *
     * @param id
     * @throws Exception
     */
    @Transactional(propagation = Propagation.NEVER, rollbackFor = Exception.class)
    public void never(int id) throws Exception {
        if (this.updateName(id)) {
            this.query("notSupport: after updateMoney name", id);
            if (this.updateMoney(id)) {
                return;
            }
        }
    }




    /**
     * 如果不存在事务，则开启一个事务运行
     *
     * 如果存在事务，则运行一个嵌套事务；
     *
     * 嵌套事务：外部事务回滚，内部事务也会被回滚；内部事务异常，外部无问题，并不会回滚外部事务
     *
     * @param id
     * @throws Exception
     */
    @Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
    public void nested(int id) throws Exception {
        if (this.updateName(id)) {
            this.query("nested: after updateMoney name", id);
            if (this.updateMoney(id)) {
                return;
            }
        }

        throw new Exception("事务回滚!!!");
    }



    @Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
    public void nested2(int id) throws Exception {
        if (this.updateName(id)) {
            this.query("nested: after updateMoney name", id);
            if (this.updateMoney(id)) {
                return;
            }
        }
    }

    public boolean updateName(int id, String name) {
        String sql = "update money set `name`='" + name + "' where id=" + id;
        jdbcTemplate.execute(sql);
        return true;
    }

    private boolean updateName(int id) {
        return updateName(id, "更新");
    }

    public void query(String tag, int id) {
        String sql = "select * from money where id=" + id;
        Map map = jdbcTemplate.queryForMap(sql);
        System.out.println(tag + " >>>> " + map);
    }

    private boolean updateMoney(int id) {
        String sql = "update money set `money`= `money` + 10 where id=" + id;
        jdbcTemplate.execute(sql);
        return false;
    }


}
