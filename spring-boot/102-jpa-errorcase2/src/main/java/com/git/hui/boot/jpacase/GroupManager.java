package com.git.hui.boot.jpacase;

import com.git.hui.boot.jpacase.error.entity.ErrorMetaGroupPo;
import com.git.hui.boot.jpacase.error.repository.ErrorGroupJPARepository;
import com.git.hui.boot.jpacase.right.entity.MetaGroupPO;
import com.git.hui.boot.jpacase.right.repository.GroupJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

/**
 * Created by @author yihui in 18:30 19/12/18.
 */
@Component
public class GroupManager {
    @Autowired
    private ErrorGroupJPARepository errorGroupJPARepository;

    @Autowired
    private GroupJPARepository groupJPARepository;


    public void test() {
        String group = UUID.randomUUID().toString().substring(0, 4);
        String profile = "dev";
        String desc = "测试jpa异常case!";
        try {
            int id = addGroup1(group, profile, desc);
            System.out.println("add1: " + errorGroupJPARepository.findById(id));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            int id2 = addGroup2(group, profile, desc);
            System.out.println("add2: " + groupJPARepository.findById(id2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer addGroup1(String group, String profile, String desc) {
        ErrorMetaGroupPo jpa = new ErrorMetaGroupPo();
        jpa.setGroup("add1: " + group);
        jpa.setDesc(desc);
        jpa.setProfile(profile);
        jpa.setDeleted(0);
        Timestamp timestamp = Timestamp.from(Instant.now());
        jpa.setCreateTime(timestamp);
        jpa.setUpdateTime(timestamp);
        ErrorMetaGroupPo res = errorGroupJPARepository.save(jpa);
        return res.getId();
    }

    public Integer addGroup2(String group, String profile, String desc) {
        MetaGroupPO jpa = new MetaGroupPO();
        jpa.setGroup("add2: " + group);
        jpa.setDesc(desc);
        jpa.setProfile(profile);
        jpa.setDeleted(0);
        Timestamp timestamp = Timestamp.from(Instant.now());
        jpa.setCreateTime(timestamp);
        jpa.setUpdateTime(timestamp);
        MetaGroupPO res = groupJPARepository.save(jpa);
        return res.getId();
    }
}
