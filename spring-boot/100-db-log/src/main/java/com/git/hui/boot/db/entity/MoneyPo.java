package com.git.hui.boot.db.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author YiHui
 * @date 2023/7/18
 */
@Data
public class MoneyPo {
    private Integer id;

    private String name;

    private Long money;

    private Integer isDeleted;

    private Timestamp createAt;

    private Timestamp updateAt;
}
