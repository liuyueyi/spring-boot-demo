package com.git.hui.boot.beanutil.copier;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

/**
 * 不推荐使用，性能较差
 *
 * @author yihui
 * @date 2021/4/7
 */
@Component
public class ApacheCopier {
    public <K, T> T copy(K source, Class<T> target) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        T res = target.newInstance();
        BeanUtils.copyProperties(res, source);
        return res;
    }
}
