package com.git.hui.boot.selfconfig;

import com.git.hui.boot.selfconfig.auto.SelfConfigContextInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author YiHui
 * @date 2023/6/20
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.addInitializers(new SelfConfigContextInitializer());
        springApplication.run(args);
    }
}
