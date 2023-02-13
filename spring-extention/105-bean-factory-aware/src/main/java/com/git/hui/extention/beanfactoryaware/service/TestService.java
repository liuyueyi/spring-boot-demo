package com.git.hui.extention.beanfactoryaware.service;

import com.git.hui.extention.beanfactoryaware.config.MyAutoConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author YiHui
 * @date 2023/2/13
 */
@Service
public class TestService {
    @Setter
    @Getter
    private String prefix;

    private MyAutoConfig config;

    @Autowired
    public void setConfig(MyAutoConfig config) {
        this.config = config;
        System.out.println("demo service 初始化data!");
    }


    public String showTest() {
        System.out.println("show test");
        return prefix + "_test:" + config.getData() + " at: " + LocalDateTime.now();
    }
}
