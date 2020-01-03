package com.git.hui.boot.jpacase.right.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by @author yihui in 18:28 19/12/18.
 */
@Data
@Entity
@Table(name = "meta_group")
public class MetaGroupPO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "`group`")
    private String group;

    @Column(name = "`profile`")
    private String profile;

    @Column(name = "`desc`")
    private String desc;

    @Column(name = "`deleted`")
    private Integer deleted;

    @Column(name = "`create_time`")
    private Timestamp createTime;

    @Column(name = "`update_time`")
    private Timestamp updateTime;
}

