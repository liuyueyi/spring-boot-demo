package com.git.hui.boot.jdbc.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 事务传递 示例demo
 * Created by @author yihui in 10:59 20/2/2.
 */
@Component
public class PropagationDemo2 {
    @Autowired
    private PropagationDemo propagationDemo;

    @Transactional(rollbackFor = Exception.class)
    public void support(int id) throws Exception {
        // 事务运行
        propagationDemo.support(id);
    }


    @Transactional(rollbackFor = Exception.class)
    public void notSupport(int id) throws Exception {
        // 挂起当前事务，以非事务方式运行
        try {
            propagationDemo.notSupport(id);
        } catch (Exception e) {
        }

        propagationDemo.query("notSupportCall: ", id);
        propagationDemo.updateName(id, "外部更新");
        propagationDemo.query("notSupportCall: ", id);
        throw new Exception("回滚");
    }

    @Transactional(rollbackFor = Exception.class)
    public void never(int id) throws Exception {
        propagationDemo.never(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public void nested(int id) throws Exception {
        propagationDemo.updateName(id, "外部事务修改");
        propagationDemo.query("nestedCall: ", id);
        try {
            propagationDemo.nested(id);
        } catch (Exception e) {
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void nested2(int id) throws Exception {
        // 嵌套事务，外部回滚，会同步回滚内部事务
        propagationDemo.updateName(id, "外部事务修改");
        propagationDemo.query("nestedCall: ", id);
        propagationDemo.nested2(id);
        throw new Exception("事务回滚");
    }

}
