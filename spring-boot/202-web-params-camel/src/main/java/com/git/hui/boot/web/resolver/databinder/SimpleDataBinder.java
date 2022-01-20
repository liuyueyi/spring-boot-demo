package com.git.hui.boot.web.resolver.databinder;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.ServletRequest;

/**
 * @author yihui
 * @date 22/1/16
 */
public class SimpleDataBinder extends ExtendedServletRequestDataBinder {

    public SimpleDataBinder(Object target, String objectName) {
        super(target, objectName);
    }

    @Override
    protected void addBindValues(MutablePropertyValues mpvs, ServletRequest request) {
        super.addBindValues(mpvs, request);
        if (!mpvs.contains("userName")) {
            mpvs.add("userName", getVal(mpvs, "user_name"));
        }
        if (!mpvs.contains("userId")) {
            mpvs.add("userId", getVal(mpvs, "user_id"));
        }
    }

    private Object getVal(MutablePropertyValues mpvs, String key) {
        PropertyValue pv = mpvs.getPropertyValue(key);
        return pv != null ? pv.getValue() : null;
    }
}
