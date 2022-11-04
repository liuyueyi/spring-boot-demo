package com.git.hui.extention.postprocessor.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

/**
 * @author YiHui
 * @date 2022/11/4
 */
public class DemoService implements InitializingBean {

    private String data;

    private BasicService basicService;

    @Value("${user.data}")
    public void setData(String data) {
        this.data = data;
        System.out.println("DemoService: -> 属性注入:" + data);
    }

    @Autowired
    public void setBasicService(BasicService basicService) {
        this.basicService = basicService;
        System.out.println("DemoService.basicService 依赖注入!");
    }

    public DemoService() {
        System.out.println("DemoService 构造方式执行");
    }

    public DemoService(String txt) {
        System.out.println("DemoService 构造方式执行:" + txt);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("DemoService#afterPropertiesSet 执行");
    }

    @PostConstruct
    public void init() {
        System.out.println("DemoService# @PostConstruct 执行");
    }

    public void initMethod() {
        System.out.println("DemoService#initMethod 执行!");
    }
}
