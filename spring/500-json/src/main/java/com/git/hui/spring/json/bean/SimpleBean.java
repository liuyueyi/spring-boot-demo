package com.git.hui.spring.json.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author yihui
 * @date 2021/7/14
 */
@Data
@Accessors(chain = true)
public class SimpleBean implements Serializable {
    private static final long serialVersionUID = -9111747337710917591L;

    private Integer userId;

    private String userName;

    private double userMoney;

    private List<String> userSkills;

    private Map<String, Object> extra;

    private String empty;

    // jackson 序列化时，如果 transient 关键字，也有 getter/setter方法，那么也会被序列化出来
//    @JsonIgnore
//    @JSONField(serialize = false, deserialize = false)
//    @Expose(serialize = false, deserialize = false)
    private transient SimpleBean self;

    public String hello = "你好";

    public SimpleBean() {
        this.self = this;
    }

    @Override
    public String toString() {
        return "SimpleBean{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userMoney=" + userMoney +
                ", userSkills=" + userSkills +
                ", extra=" + extra +
                ", empty='" + empty + '\'' +
                '}';
    }

//
//    public Integer getUserId() {
//        return userId;
//    }
//
//    public SimpleBean setUserId(Integer userId) {
//        this.userId = userId;
//        return this;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public SimpleBean setUserName(String userName) {
//        this.userName = userName;
//        return this;
//    }
//
//    public double getUserMoney() {
//        return userMoney;
//    }
//
//    public SimpleBean setUserMoney(double userMoney) {
//        this.userMoney = userMoney;
//        return this;
//    }
//
//    public List<String> getUserSkills() {
//        return userSkills;
//    }
//
//    public SimpleBean setUserSkills(List<String> userSkills) {
//        this.userSkills = userSkills;
//        return this;
//    }
//
//    public Map<String, Object> getExtra() {
//        return extra;
//    }
//
//    public SimpleBean setExtra(Map<String, Object> extra) {
//        this.extra = extra;
//        return this;
//    }
//
//    public String getEmpty() {
//        return empty;
//    }
//
//    public void setEmpty(String empty) {
//        this.empty = empty;
//    }
}
