package com.git.hui.boot.dynamicbean;

import com.git.hui.boot.dynamicbean.manual.ManualBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * 用于测试bean加载顺序导致手动注册的bean无法找到的问题
 * Created by @author yihui in 19:06 19/12/2.
 */
@DependsOn("beanRegisterAutoConf")
@Component
public class AnoOrderDIBean {
    @Autowired
    private ManualBean manualBean;

    public AnoOrderDIBean() {
        System.out.println("AnoOrderDIBean init: " + System.currentTimeMillis());
    }

    public String print() {
        return "[AnoOrderDIBean] print！！！ manualBean == null ? " + (manualBean == null);
    }
}
