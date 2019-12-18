package com.git.hui.boot.jpacase.manager;

import com.git.hui.boot.jpacase.entity.MetaGroupPO;
import com.git.hui.boot.jpacase.repository.GroupJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * Created by @author yihui in 18:30 19/12/18.
 */
@Component
public class GroupManager {
    @Autowired
    private GroupJPARepository groupJPARepository;

    public GroupManager() {
        System.out.println("group manager init!");
    }

    public MetaGroupPO getOnlineGroup(String group, String profile) {
        return groupJPARepository.findByGroupAndProfileAndDeleted(group, profile, 0);
    }

    public Integer addGroup(String group, String profile, String desc) {
        MetaGroupPO jpa = new MetaGroupPO();
        jpa.setGroup(group);
        jpa.setDesc(desc);
        jpa.setProfile(profile);
        jpa.setDeleted(0);
        Timestamp timestamp = Timestamp.from(Instant.now());
        jpa.setCreateTime(timestamp);
        jpa.setUpdateTime(timestamp);
        MetaGroupPO res = groupJPARepository.save(jpa);
        return res.getId();
    }

    @Transactional
    public boolean updateGroup(Integer groupId, String desc) {
        return groupJPARepository.updateDesc(groupId, desc) > 0;
    }

    @Transactional
    public boolean deleteGroup(Integer groupId) {
        return groupJPARepository.logicDeleted(groupId) > 0;
    }
}
