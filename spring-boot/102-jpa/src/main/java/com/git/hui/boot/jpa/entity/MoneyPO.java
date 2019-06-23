package com.git.hui.boot.jpa.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by @author yihui in 21:01 19/6/10.
 */
@Data
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "money")
public class MoneyPO {
    @Id
    // 如果是auto，则会报异常 Table 'mysql.hibernate_sequence' doesn't exist
    // @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "money")
    private Long money;

    @Column(name = "is_deleted")
    private Byte isDeleted;

    @Column(name = "create_at")
    @CreatedDate
    private Timestamp createAt;

    @Column(name = "update_at")
    @CreatedDate
    private Timestamp updateAt;

}
