package com.git.hui.boot.bean.autoload.simple;

import com.git.hui.boot.autoconfig.AutoBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 依赖第三方的bean
 * Created by @author yihui in 17:18 18/10/10.
 */
@Slf4j
@Component
public class DependAutoBean {
    private final AutoBean autoBean;

    public DependAutoBean(AutoBean autoBean) {
        this.autoBean = autoBean;
        log.info("DependAutoBean load time: {}", System.currentTimeMillis());
    }
}
