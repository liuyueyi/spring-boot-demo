package com.git.hui.boot.beanutil.copier;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.Editor;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wuzebang
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
//        return BeanUtil.toBean(source, target);
        return copyAndParse(source, target);
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
        Map<String, String> mapper = new HashMap<>();
        mapper.put("user_name", "userName");
        BeanUtil.copyProperties(source, res, CopyOptions.create().setFieldMapping(mapper));
        return res;
    }
}
