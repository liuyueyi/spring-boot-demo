package com.git.hui.extention.beanfactoryaware;

import com.git.hui.extention.beanfactoryaware.service.BeanCacheService;
import com.git.hui.extention.beanfactoryaware.service.DemoService;
import com.git.hui.extention.beanfactoryaware.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author YiHui
 * @date 2023/2/13
 */
@SpringBootApplication
public class Application implements ApplicationRunner {
    @Autowired
    private BeanCacheService beanCacheService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(beanCacheService.findBean(DemoService.class).showDemo());
        System.out.println(beanCacheService.findBean(TestService.class).showTest());;
        System.out.println("---------- over -------------");
    }
}
