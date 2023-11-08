package com.git.hui.boot.selfconfig.property;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注册自定义的配置源
 *
 * @author YiHui
 * @date 2023/6/25
 */
public class SelfConfigContext {
    private static volatile SelfConfigContext instance = new SelfConfigContext();

    public static SelfConfigContext getInstance() {
        return instance;
    }

    private Map<String, Object> cache = new ConcurrentHashMap<>();

    public Map<String, Object> getCache() {
        return cache;
    }

    private SelfConfigContext() {
        // 将内存的配置信息设置为最高优先级
        cache.put("config.type", 33);
        cache.put("config.wechat", "一灰灰blog");
        cache.put("config.github", "liuyueyi");
    }


    /**
     * 更新配置
     *
     * @param key
     * @param val
     */
    public void updateConfig(String key, Object val) {
        cache.put(key, val);
        ConfigChangeListener.publishConfigChangeEvent(key);
    }
}
