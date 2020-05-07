package com.git.hui.boot.dconfig.demo.meta;

import com.git.hui.boot.dynamic.config.container.AbstractMetaValHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by @author yihui in 22:00 20/4/21.
 */
@Component
public class MetaPropertyHolder extends AbstractMetaValHolder {
    public Map<String, String> metas = new HashMap<>(8);

    {
        metas.put("name", "一灰灰");
        metas.put("blog", "https://blog.hhui.top");
        metas.put("age", "18");
    }

    @Override
    public String getProperty(String key) {
        return metas.getOrDefault(key, "");
    }

    @Override
    public String doUpdateProperty(String key, String value) {
        return metas.put(key, value);
    }
}
