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
public class DemoService {
    @Setter
    @Getter
    private String prefix;

    private MyAutoConfig config;

    @Autowired
    public void setConfig(MyAutoConfig config) {
        this.config = config;
        System.out.println("demo service 初始化data!");
    }

    public String showDemo() {
        System.out.println("show demo!");
        return prefix + "_demo:" + config.getData() + " at: " + LocalDateTime.now().toString();
    }

}
