package com.git.hui.boot.web.resolver.databinder;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

/**
 * 一个简单的，直接写死转换规则的处理方式
 * @author yihui
 * @date 22/1/16
 */
public class SimpleArgumentProcessor extends ServletModelAttributeMethodProcessor {
    public SimpleArgumentProcessor(boolean annotationNotRequired) {
        super(annotationNotRequired);
    }

    @Override
    protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest nativeWebRequest) {
        Object target = binder.getTarget();
        SimpleDataBinder dataBinder = new SimpleDataBinder(target, binder.getObjectName());
        super.bindRequestParameters(dataBinder, nativeWebRequest);
    }
}
