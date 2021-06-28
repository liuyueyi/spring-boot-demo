package com.git.hui.boot.beanutil.copier;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yihui
 * @date 2021/4/7
 */
@Component
public class HutoolCopier {

    /**
     * bean 对象转换
     *
     * @param source
     * @param target
     * @param <K>
     * @param <T>
     * @return
     */
    public <K, T> T copy(K source, Class<T> target) throws Exception {
        return BeanUtil.toBean(source, target);
    }


    /**
     * 驼峰转换
     *
     * @param source
     * @param target
     * @param <K>
     * @param <T>
     * @return
     */
    public <K, T> T copyAndParse(K source, Class<T> target) throws Exception {
        T res = target.newInstance();
        // 下划线转驼峰
        BeanUtil.copyProperties(source, res, getCopyOptions(source.getClass()));
        return res;
    }

    private Map<Class, CopyOptions> cacheMap = new HashMap<>();


    private CopyOptions getCopyOptions(Class source) {
        CopyOptions options = cacheMap.get(source);
        if (options == null) {
            // 不加锁，我们认为重复执行不会比并发加锁带来的开销大
            options = CopyOptions.create().setFieldMapping(buildFieldMapper(source));
            cacheMap.put(source, options);
        }
        return options;
    }

    /**
     * 这个是可以做缓存，避免每次重新创建
     *
     * @param source
     * @return
     */
    private Map<String, String> buildFieldMapper(Class source) {
        PropertyDescriptor[] properties = ReflectUtils.getBeanProperties(source);
        Map<String, String> map = new HashMap<>();
        for (PropertyDescriptor target : properties) {
            String name = target.getName();
            String camel = StrUtil.toCamelCase(name);
            if (!name.equalsIgnoreCase(camel)) {
                map.put(name, camel);
            }
            String under = StrUtil.toUnderlineCase(name);
            if (!name.equalsIgnoreCase(under)) {
                map.put(name, under);
            }
        }
        return map;
    }
}
