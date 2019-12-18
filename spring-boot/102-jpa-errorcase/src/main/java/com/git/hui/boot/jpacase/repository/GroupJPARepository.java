package com.git.hui.boot.jpacase.repository;

import com.git.hui.boot.jpacase.entity.MetaGroupPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by @author yihui in 19:12 19/12/16.
 */
public interface GroupJPARepository extends JpaRepository<MetaGroupPO, Integer> {

    List<MetaGroupPO> findByProfile(String profile);

    MetaGroupPO findByGroupAndProfileAndDeleted(String group, String profile, Integer deleted);

    @Modifying
    @Query("update MetaGroupPO m set m.desc=?2 where m.id=?1")
    int updateDesc(int groupId, String desc);

    @Modifying
    @Query("update MetaGroupPO m set m.deleted=1 where m.id=?1")
    int logicDeleted(int groupId);
}
