package com.git.hui.boot.db.interceptor;

import org.springframework.util.ClassUtils;

/**
 * @author YiHui
 * @date 2023/5/28
 */
public class DruidCheckUtil {

    /**
     * 判断是否包含durid相关的数据包
     *
     * @return
     */
    public static boolean hasDruidPkg() {
        return ClassUtils.isPresent("com.alibaba.druid.pool.DruidDataSource", DruidCheckUtil.class.getClassLoader());
    }

}
