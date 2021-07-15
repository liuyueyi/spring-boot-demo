package com.git.hui.spring.json.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author yihui
 * @date 2021/7/14
 */
@Data
@Accessors(chain = true)
public class GenericBean<T> implements Serializable {
    private static final long serialVersionUID = 929359940822063109L;

    private Integer userId;

    private String userName;

    private double userMoney;

    private List<String> userSkills;

    private T extra;

    @JsonIgnore
    @JSONField(serialize = false, deserialize = false)
    @Expose(serialize = false, deserialize = false)
    private transient GenericBean self;

    public GenericBean() {
        this.self = this;
    }

    @Override
    public String toString() {
        return "GenericBean{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userMoney=" + userMoney +
                ", skills=" + userSkills +
                ", extra=" + extra +
                '}';
    }
}
