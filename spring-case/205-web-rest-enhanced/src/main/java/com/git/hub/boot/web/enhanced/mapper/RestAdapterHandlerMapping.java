package com.git.hub.boot.web.enhanced.mapper;

import com.git.hub.boot.web.enhanced.anno.RestService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * Created by @author yihui in 10:59 20/4/5.
 */
public class RestAdapterHandlerMapping extends RequestMappingHandlerMapping {
    @Override
    public Map<RequestMappingInfo, HandlerMethod> getHandlerMethods() {
        return super.getHandlerMethods();
    }

    @Override
    public void registerMapping(RequestMappingInfo mapping, Object handler, Method method) {
        super.registerMapping(mapping, handler, method);
    }

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        System.out.println("current handlerType: " + handlerType.getName());
        Class target = getMappingClass(handlerType);
        if (target == null) {
            return super.getMappingForMethod(method, handlerType);
        }

        String prefix = target.getSimpleName();
        RequestMappingInfo info  = createRequestMappingInfo(method, prefix, false);
        if (info != null) {
            return RequestMappingInfo.paths(prefix).build().combine(info);
        }
        return super.getMappingForMethod(method, handlerType);
    }

    private Class getMappingClass(Class<?> handlerType) {
        if (handlerType.isAnnotationPresent(RestService.class)) {
            return handlerType;
        }

        for (Class clz : handlerType.getInterfaces()) {
            if (clz.isAnnotationPresent(RestService.class)) {
                return clz;
            }
        }
        return null;
    }

    protected RequestMappingInfo createRequestMappingInfo(Method method, String prefix, boolean get) {
        RequestMappingInfo.Builder builder =
                RequestMappingInfo.paths(method.getName()).methods(get ? RequestMethod.GET : RequestMethod.POST);
        System.out.println("support url: " + prefix + "/" + method.getName() + (get ? "-X GET" : "-X POST"));
        if (!get) {
            Parameter[] parameters = method.getParameters();
            for (Parameter p : parameters) {
                if (p.isAnnotationPresent(RequestBody.class)) {
                    builder.produces(MediaType.APPLICATION_JSON_VALUE);
                    break;
                }
            }
        }

        return builder.build();
    }

}

