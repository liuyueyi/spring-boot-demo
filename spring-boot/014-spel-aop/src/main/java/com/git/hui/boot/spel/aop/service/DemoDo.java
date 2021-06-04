package com.git.hui.boot.spel.aop.service;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author yihui
 * @date 21/6/4
 */
@Data
@Accessors(chain = true)
public class DemoDo {

    private String name;

    private Integer age;

    public String getName() {
        return name;
    }
}
