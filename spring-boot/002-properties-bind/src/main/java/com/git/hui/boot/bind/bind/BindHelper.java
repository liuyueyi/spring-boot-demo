package com.git.hui.boot.bind.bind;

import com.git.hui.boot.bind.config.Pwd;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 手动实现配置加载配置类的方式
 *
 * @author YiHui
 * @date 2023/5/12
 */
@Component
public class BindHelper implements EnvironmentAware {
    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void bindInfo() {
        // 直接将前缀对应的配置，加载到指定的对象中
        Pwd config = Binder.get(environment).bindOrCreate("bind.auto", Pwd.class);
        System.out.println(config);
    }
}
