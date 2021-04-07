package com.git.hui.boot.beanutil.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wuzebang
 * @date 2021/4/7
 */
@Data
public class Target {
    private Integer id;
    private String userName;
    private Double price;
    private List<Long> ids;
    private BigDecimal market;
}
