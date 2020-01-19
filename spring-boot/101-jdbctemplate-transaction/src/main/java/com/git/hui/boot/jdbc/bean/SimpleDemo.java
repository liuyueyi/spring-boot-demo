package com.git.hui.boot.jdbc.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * 声明式注解，通过 @Transactionl 完成事务的声明，注意只有公共方法 or 类上才会生效；外部直接调用，默认抛运行异常才会有效；多线程时不会生效
 *
 * Created by @author yihui in 14:34 20/1/17.
 */
@Service
public class SimpleDemo {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        String sql = "replace into money (id, name, money) values (120, '初始化', 200)," +
                "(130, '初始化', 200)," +
                "(140, '初始化', 200)," +
                "(150, '初始化', 200)";
        jdbcTemplate.execute(sql);
    }

    public void test() {
        System.out.println("============ 事务不生效 start ========== ");
        query("transaction before", 120);
        try {
            testRuntimeExceptionTrans(120);
        } catch (Exception e) {
            //            e.printStackTrace();
        }
        query("transaction end", 120);
        System.out.println("============ 事务不生效 end ========== \n");
    }

    /**
     * 运行异常导致回滚
     *
     * @return
     */
    @Transactional
    public boolean testRuntimeExceptionTrans(int id) {
        if (this.updateName(id)) {
            this.query("after updateMoney name", id);
            if (this.updateMoney(id)) {
                return true;
            }
        }

        throw new RuntimeException("更新失败，回滚!");
    }

    @Transactional
    public boolean testNormalException(int id) throws Exception {
        if (this.updateName(id)) {
            this.query("after updateMoney name", id);
            if (this.updateMoney(id)) {
                return true;
            }
        }

        throw new Exception("声明异常");
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean testSpecialException(int id) throws Exception {
        if (this.updateName(id)) {
            this.query("after updateMoney name", id);
            if (this.updateMoney(id)) {
                return true;
            }
        }

        throw new IllegalArgumentException("参数异常");
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

    private boolean updateMoney(int id) {
        String sql = "update money set `money`= `money` + 10 where id=" + id;
        jdbcTemplate.execute(sql);
        return false;
    }


}
