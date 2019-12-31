package com.git.hui.boot.mybatis.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * Created by @author yihui in 21:01 19/6/10.
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
