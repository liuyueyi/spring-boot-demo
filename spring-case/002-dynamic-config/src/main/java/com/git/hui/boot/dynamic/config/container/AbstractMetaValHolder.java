package com.git.hui.boot.dynamic.config.container;

import com.git.hui.boot.dynamic.config.event.MetaChangeEvent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by @author yihui in 10:17 20/5/6.
 */
public abstract class AbstractMetaValHolder implements MetaValHolder, ApplicationContextAware {

    protected ApplicationContext applicationContext;

    public void updateProperty(String key, String value) {
        String old = this.doUpdateProperty(key, value);
        this.applicationContext.publishEvent(new MetaChangeEvent(this, key, old, value));
    }

    /**
     * 更新配置
     *
     * @param key
     * @param value
     * @return
     */
    public abstract String doUpdateProperty(String key, String value);

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
