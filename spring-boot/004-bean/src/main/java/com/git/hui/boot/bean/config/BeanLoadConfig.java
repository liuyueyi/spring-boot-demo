package com.git.hui.boot.bean.config;

import com.git.hui.boot.bean.autoload.factory.DemoFactoryBean;
import com.git.hui.boot.bean.autoload.factory.FacDemoBean;
import com.git.hui.boot.bean.autoload.simple.AnotherConfigBean;
import com.git.hui.boot.bean.autoload.simple.ConfigDemoBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by @author yihui in 17:02 18/9/30.
 */
@Configuration
public class BeanLoadConfig {
    @Bean
    public ConfigDemoBean configDemoBean() {
        return new ConfigDemoBean();
    }

    @Bean
    public AnotherConfigBean anotherConfigBean() {
        return new AnotherConfigBean();
    }

    @Bean
    public DemoFactoryBean demoFactoryBean() {
        return new DemoFactoryBean();
    }

    @Bean
    public FacDemoBean facDemoBean(DemoFactoryBean demoFactoryBean) throws Exception {
        return demoFactoryBean.getObject();
    }
}
