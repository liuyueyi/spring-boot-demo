package com.git.hui.weblfux.params;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by @author yihui in 20:02 20/9/7.
 */
@Data
public class UserDO implements Serializable {
    private static final long serialVersionUID = 1947862817960095387L;
    private String name;
    private Integer age;
}
