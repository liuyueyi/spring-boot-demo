package com.git.hui.boot.bean;

import com.git.hui.boot.bean.selfload.bean.AnoBean;
import com.git.hui.boot.bean.selfload.engine.SelfBeanLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Created by @author yihui in 09:09 18/9/29.
 */
@Slf4j
@SpringBootApplication
public class Application {

    public Application(ConfigurableApplicationContext applicationContext) {
        selfLoad(applicationContext);
    }


    private void selfLoad(ConfigurableApplicationContext applicationContext) {
        SelfBeanLoader.autoLoadBean(applicationContext);
        AnoBean bean = applicationContext.getBean(AnoBean.class);
        log.info("bean: {}", bean.sayHello("一灰灰Blog"));
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
