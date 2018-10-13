package com.git.hui.boot.dynamicbean.bean.manual;

import com.git.hui.boot.dynamicbean.manual.ManualBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 14:40 18/10/13.
 */
@Slf4j
@Component
public class AnoOriginBean {

    @Autowired
    private ManualBean manualBean;

    public AnoOriginBean() {
        System.out.println("AnoOriginBean init: " + System.currentTimeMillis());
    }

    public String print() {
        return "[AnoOriginBean] print！！！ manualBean == null ? " + (manualBean == null);
    }
}
