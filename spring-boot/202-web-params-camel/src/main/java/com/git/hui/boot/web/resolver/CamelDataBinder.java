package com.git.hui.boot.web.resolver;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.ServletRequest;

/**
 * @author yihui
 * @data 2022/1/16
 */
public class CamelDataBinder extends ExtendedServletRequestDataBinder {

    public CamelDataBinder(Object target) {
        super(target);
    }

    @Override
    protected void addBindValues(MutablePropertyValues mpvs, ServletRequest request) {
        super.addBindValues(mpvs, request);
        PropertyValue[] values = mpvs.getPropertyValues();
        for (PropertyValue v : values) {
            String key = v.getName();
            String newKey = toUnderOrCamelName(key);
            mpvs.add(newKey, v.getValue());
        }
    }

    private String toUnderOrCamelName(String name) {
        String cam = CamelUtil.toCamelCase(name);
        if (cam.equals(name)) {
            return CamelUtil.toUnderCase(name);
        }
        return cam;
    }

}
