package com.git.hui.boot.dynamicbean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 *
 * Created by @author yihui in 09:43 18/10/13.
 */
@SpringBootApplication
public class Application {

    public Application(ApplicationContext applicationContext) {
//        registerManualBean((ConfigurableApplicationContext) applicationContext);
    }

//    private void registerManualBean(ConfigurableApplicationContext applicationContext) {
//        // 主动注册一个没什么依赖的Bean
//        ManualBean manualBean = ManualRegistBeanUtil.registerBean(applicationContext, "manualBean", ManualBean.class);
//        manualBean.print("test print manualBean");
//
//        ManualDIBean manualDIBean = ManualRegistBeanUtil.registerBean(applicationContext, "manualDIBean",
//                ManualDIBean.class, "依赖OriginBean的自定义Bean");
//        manualDIBean.print("test print manualDIBean");
//    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
