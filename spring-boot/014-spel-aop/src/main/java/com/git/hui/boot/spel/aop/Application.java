package com.git.hui.boot.spel.aop;

import com.git.hui.boot.spel.aop.service.DemoDo;
import com.git.hui.boot.spel.aop.service.HelloService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yihui
 * @date 21/6/4
 */
@SpringBootApplication
public class Application {

    public Application(HelloService helloService) {
        helloService.say(new DemoDo().setName("一灰灰blog").setAge(18), "welcome");

        String ans = helloService.hello("一灰灰", helloService);
        System.out.println(ans);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
