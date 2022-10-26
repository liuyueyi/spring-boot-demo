package com.git.hui.extention.definition.normal;

import com.git.hui.extention.definition.register.DemoBean;
import com.git.hui.extention.definition.register.DemoBeanWrapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author YiHui
 * @date 2022/10/26
 */
@Component
public class NormalBean implements InitializingBean {
    @Autowired
    private DemoBean demoBean;
    @Autowired
    private DemoBeanWrapper demoBeanWrapper;

    @PostConstruct
    public void show() {
        System.out.println("NormalBean: postConstruct");
        System.out.println(demoBean.getInitCode());
        System.out.println(demoBeanWrapper.getInitCode());
    }

    public void init() {
        System.out.println("NormalBean: method init");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("NormalBean: afterPropertiesSet");
    }
}
