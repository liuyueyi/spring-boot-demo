package com.git.hui.boot.selfconfig.property;

import com.git.hui.boot.selfconfig.util.SpringUtil;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author YiHui
 * @date 2023/6/26
 */
@Component
public class ConfigChangeListener implements ApplicationListener<ConfigChangeListener.ConfigChangeEvent> {

    @Override
    public void onApplicationEvent(ConfigChangeEvent configChangeEvent) {
        SpringValueRegistry.updateValue(configChangeEvent.getKey());
    }

    public static void publishConfigChangeEvent(String key) {
        SpringUtil.getApplicationContext().publishEvent(new ConfigChangeEvent(Thread.currentThread().getStackTrace()[0], key));
    }

    @Getter
    public static class ConfigChangeEvent extends ApplicationEvent {
        private String key;

        public ConfigChangeEvent(Object source, String key) {
            super(source);
            this.key = key;
        }
    }
}
