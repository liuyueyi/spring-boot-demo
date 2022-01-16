package com.git.hui.boot.web.resolver.databinder;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.ServletRequest;
import java.util.Map;

/**
 * 参数解析为POJO时，支持驼峰与下划线的映射
 *
 * @author yihui
 * @data 2022/1/16
 */
public class ParamDataBinder extends ExtendedServletRequestDataBinder {
    private final Map<String, String> renameMapping;

    public ParamDataBinder(Object target, String objectName, Map<String, String> renameMapping) {
        super(target, objectName);
        this.renameMapping = renameMapping;
    }

    @Override
    protected void addBindValues(MutablePropertyValues mpvs, ServletRequest request) {
        super.addBindValues(mpvs, request);
        for (Map.Entry<String, String> entry : renameMapping.entrySet()) {
            String from = entry.getKey();
            String to = entry.getValue();
            if (mpvs.contains(from)) {
                mpvs.add(to, mpvs.getPropertyValue(from).getValue());
            }
        }
    }

}
