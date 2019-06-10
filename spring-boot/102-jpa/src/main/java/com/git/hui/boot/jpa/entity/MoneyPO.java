package com.git.hui.boot.jpa.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by @author yihui in 21:01 19/6/10.
 */
@Data
@Entity
@Table(name = "money")
public class MoneyPO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "money")
    private Long money;

    @Column(name = "is_deleted")
    private Byte isDeleted;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "update_at")
    private Date updateAt;
}
