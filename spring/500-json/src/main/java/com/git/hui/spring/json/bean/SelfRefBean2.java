package com.git.hui.spring.json.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author yihui
 * @date 2021/7/15
 */
@Data
@Accessors(chain = true)
public class SelfRefBean2 implements Serializable {
    private static final long serialVersionUID = -2808787760792080759L;

    private String name;

    private SelfRefBean bean;

    @Override
    public String toString() {
        return "SelfRefBean{" +
                "name='" + name + '\'' +
                '}';
    }
}
