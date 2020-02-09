package com.git.hui.boot.jdbc.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * Created by @author yihui in 16:00 20/1/17.
 */
@Service
public class ManualDemo {
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        String sql = "replace into money (id, name, money) values (220, '初始化', 200)," + "(230, '初始化', 200)," +
                "(240, '初始化', 200)," + "(250, '初始化', 200)";
        jdbcTemplate.execute(sql);
    }


    public void testTransaction(int id) {
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus transactionStatus) {
                try {
                    return doUpdate(id);
                } catch (Exception e) {
                    transactionStatus.setRollbackOnly();
                    return false;
                }
            }
        });
    }


    private boolean doUpdate(int id) throws Exception {
        if (this.updateName(id)) {
            this.query("after updateMoney name", id);
            if (this.updateMoney(id)) {
                return true;
            }
        }

        throw new Exception("参数异常");
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
