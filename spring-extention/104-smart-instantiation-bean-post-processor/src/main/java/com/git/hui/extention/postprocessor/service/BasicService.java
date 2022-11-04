package com.git.hui.extention.postprocessor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author YiHui
 * @date 2022/11/4
 */
@Service
public class BasicService {

    @Autowired
    private DemoService demoService;
    private DemoService2 demoService2;

    public BasicService() {
        System.out.println("BasicService 使用无参进行构建");
    }

    public BasicService(DemoService2 demoService2) {
        this.demoService2 = demoService2;
        System.out.println("BasicServiec 使用demoService传参构建");
    }

    @PostConstruct
    public void show() {
        System.out.println("BasicService#show: " + demoService + "|" + demoService2);
    }

}
