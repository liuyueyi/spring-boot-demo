package com.git.hui.boot.dynamicbean.bean.auto;

import com.git.hui.boot.dynamicbean.auto.AutoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 16:19 18/10/13.
 */
@Component
public class AnoAutoOriginBean {
    @Autowired
    private AutoBean autoBean;

    public AnoAutoOriginBean() {
        System.out.println("AnoAutoOriginBean init: " + System.currentTimeMillis());
    }

    public String print() {
        return "[AnoAutoOriginBean] print！！！ autoBean == null ? " + (autoBean == null);
    }
}
