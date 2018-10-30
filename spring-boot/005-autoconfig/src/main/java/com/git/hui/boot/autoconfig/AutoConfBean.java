package com.git.hui.boot.autoconfig;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

/**
 * Created by @author yihui in 18:54 18/10/12.
 */
@Slf4j
public class AutoConfBean {
    private String name;

    public AutoConfBean(String name) {
        this.name = name;
        log.info("AutoConfBean load time: {}", System.currentTimeMillis());
    }

    public String getName() {
        return name;
    }

    @PostConstruct
    public void register() {
        BeanWrapper.init(this);
    }
}
