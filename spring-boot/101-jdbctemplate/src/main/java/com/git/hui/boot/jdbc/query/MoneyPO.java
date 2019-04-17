package com.git.hui.boot.jdbc.query;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by @author yihui in 20:37 19/4/15.
 */
@Data
public class MoneyPO implements Serializable {
    private static final long serialVersionUID = -5423883314375017670L;
    private Integer id;
    private String name;
    private Integer money;
    private boolean isDeleted;
    private Long created;
    private Long updated;
}