package com.git.hui.boot.beanutil.copier;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * @author wuzebang
 * @date 2021/4/7
 */
@Component
public class SpringBeanCopier {

    /**
     * 对象转换
     *
     * @param source
     * @param target
     * @param <K>
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public <K, T> T copy(K source, Class<T> target) throws IllegalAccessException, InstantiationException {
        T res = target.newInstance();
        BeanUtils.copyProperties(source, res);
        return res;
    }
}
