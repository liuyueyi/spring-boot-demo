package com.git.hui.boot.jpacase.app2;

import com.git.hui.boot.jpacase.app.ErrorApplication;
import com.git.hui.boot.jpacase.entity.MetaGroupPO;
import com.git.hui.boot.jpacase.manager.GroupManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by @author yihui in 19:26 19/12/18.
 */
@SpringBootApplication
public class TrueApplication {

    public TrueApplication(GroupManager groupManager) {
        int groupId = groupManager.addGroup("true-group", "dev", "正确写入!!!");
        System.out.println("add groupId: " + groupId);
        MetaGroupPO po = groupManager.getOnlineGroup("true-group", "dev");
        System.out.println(po);
    }

    public static void main(String[] args) {
        SpringApplication.run(ErrorApplication.class);
    }
}
