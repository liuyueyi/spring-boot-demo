package com.git.hui.boot.web.mapper;

import com.git.hui.boot.web.common.AdapterController;
import com.git.hui.boot.web.common.AdapterGet;
import com.git.hui.boot.web.common.AdapterPost;
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
        if (handlerType.isAnnotationPresent(AdapterController.class)) {
            String prefix = handlerType.getSimpleName();

            RequestMappingInfo info = null;
            if (method.isAnnotationPresent(AdapterGet.class)) {
                info = createRequestMappingInfo(method, true);
            } else if (method.isAnnotationPresent(AdapterPost.class)) {
                info = createRequestMappingInfo(method, false);
            }

            if (info != null) {
                return RequestMappingInfo.paths(prefix).build().combine(info);
            }
        }

        return super.getMappingForMethod(method, handlerType);
    }

    protected RequestMappingInfo createRequestMappingInfo(Method method, boolean get) {
        RequestMappingInfo.Builder builder =
                RequestMappingInfo.paths(method.getName()).methods(get ? RequestMethod.GET : RequestMethod.POST);
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

