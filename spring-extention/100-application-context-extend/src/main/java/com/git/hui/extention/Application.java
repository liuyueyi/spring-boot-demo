package com.git.hui.extention;

import com.git.hui.extention.context.ApplicationContextInitializer01;
import com.git.hui.extention.context.ApplicationContextInitializer02;
import com.git.hui.extention.context.ApplicationContextInitializer03;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author YiHui
 * @date 2022/9/27
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // 1. 说明，自定义实现的优先级：  配置文件注册 > SPI注册 > 启动方式注册
        // 2. 相同的注册方式，可以通过 @Order 注解进行修饰
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.addInitializers(new ApplicationContextInitializer01(),  new ApplicationContextInitializer02());
        try (ConfigurableApplicationContext context = springApplication.run(args)) {
        }
    }
}
