package com.git.hui.schema.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @author YiHui
 * @date 2022/12/21
 */
@Data
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "user3")
public class User {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "third_account_id")
    private String thirdAccountId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "login_type")
    private Integer loginType;

    @Column(name = "deleted")
    private Integer deleted;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "update_time")
    private Timestamp updateTime;
}
