package com.git.hui.extention.postprocessor;

import com.git.hui.extention.postprocessor.service.DemoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author YiHui
 * @date 2022/11/4
 */
@SpringBootApplication
public class Application {

    @Bean(name = "demoService", initMethod = "initMethod")
    public DemoService demoService() {
        return new DemoService();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
