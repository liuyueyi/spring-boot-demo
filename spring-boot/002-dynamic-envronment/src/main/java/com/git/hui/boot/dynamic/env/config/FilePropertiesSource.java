package com.git.hui.boot.dynamic.env.config;

import org.springframework.core.env.MapPropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 这里以从自定义的配置文件中读取配置，来演示配置的数据源自定义（如果希望从redis/db/http获取配置，只需要修改下面的refreshSource；或者修改 getProperty）
 *
 * @author yihui
 * @date 2021/6/9
 */
public class FilePropertiesSource extends MapPropertySource {
    public FilePropertiesSource(String name, Map<String, Object> source) {
        super(name, source);
    }

    public FilePropertiesSource() {
        this("filePropertiesSource", new HashMap<>());
    }

    // 这种方式，适用于一次捞取所有的配置，然后从内存中查询对应的配置，提高服务性能
    // 10s 更新一次
    @PostConstruct
    @Scheduled(fixedRate = 10_000)
    public void refreshSource() throws IOException {
        String ans =
                FileCopyUtils.copyToString(new InputStreamReader(FilePropertiesSource.class.getClassLoader().getResourceAsStream("kv.properties")));
        Map<String, Object> map = new HashMap<>();
        for (String sub : ans.split("\n")) {
            if (sub.isEmpty()) {
                continue;
            }
            String[] kv = StringUtils.split(sub, "=");
            if (kv.length != 2) {
                continue;
            }

            map.put(kv[0].trim(), kv[1].trim());
        }

        source.clear();
        source.putAll(map);
    }

    /**
     * 覆盖这个方法，适用于实时获取配置
     *
     * @param name
     * @return
     */
    @Override
    public Object getProperty(String name) {
        return super.getProperty(name);
    }
}
