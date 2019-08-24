package com.git.hui.boot.web.rest;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by @author yihui in 16:50 19/8/23.
 */
@Data
public class BaseReqDO implements Serializable {
    private static final long serialVersionUID = 8706843673978981262L;

    private String name;

    private Integer age;

    private List<Integer> uIds;
}
