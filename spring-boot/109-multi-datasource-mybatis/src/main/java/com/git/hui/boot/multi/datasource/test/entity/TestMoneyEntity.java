package com.git.hui.boot.multi.datasource.test.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author yihui
 * @date 20/12/27
 */
@Data
public class TestMoneyEntity {
    private Integer id;

    private String name;

    private Long money;

    private Integer isDeleted;

    private Timestamp createAt;

    private Timestamp updateAt;
}
