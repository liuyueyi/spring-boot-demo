package com.git.hui.boot.mybatis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Created by @author yihui in 21:01 19/6/10.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyPo {
    private Integer id;

    private String name;

    private Long money;

    private Integer isDeleted;

    private Timestamp createAt;

    private Timestamp updateAt;

    public MoneyPo(String name, Long money, Integer isDeleted) {
        this.name = name;
        this.money = money;
        this.isDeleted = isDeleted;
    }
}
