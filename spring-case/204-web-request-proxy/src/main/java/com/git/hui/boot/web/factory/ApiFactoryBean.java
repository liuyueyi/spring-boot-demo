package com.git.hui.boot.web.factory;

import com.alibaba.fastjson.JSONObject;
import com.git.hui.boot.web.util.EnvironmentUtil;
import com.git.hui.boot.web.util.ProxyUtil;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author yihui
 * @date 2021/3/5
 */
public class ApiFactoryBean<T> implements FactoryBean<T> {
    private Class<T> targetClass;

    public ApiFactoryBean(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getObject() throws Exception {
        return ProxyUtil.newProxyInstance(targetClass, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName().equalsIgnoreCase("toString")) {
                    return method.invoke(proxy, args);
                }

                System.out.println("do before " + method.getName() + " | " + proxy.getClass() + " | " + Thread.currentThread());
                try {
                    RestTemplate restTemplate = new RestTemplate();
                    MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();

                    for (int index = 0; index < args.length; index++) {
                        Parameter p = method.getParameters()[index];
                        params.add(p.getName(), args[index]);
                    }

                    String url = EnvironmentUtil.getProperties("api.host") + targetClass.getSimpleName() + "/" + method.getName();
                    System.out.println("req url: " + url);
                    String response = restTemplate.postForObject(url, params, String.class);
                    if (method.getReturnType() == String.class) {
                        return response;
                    }

                    return JSONObject.parseObject(response, method.getReturnType());
                } finally {
                    System.out.println("do after " + method.getName() + " | " + Thread.currentThread());
                }
            }
        });
    }

    @Override
    public Class<?> getObjectType() {
        return targetClass;
    }
}
