package com.git.hui.boot.listener;

import com.git.hui.boot.listener.basic.MsgPublisher;
import com.git.hui.boot.listener.demo.DemoBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by @author yihui in 08:47 19/8/21.
 */
@SpringBootApplication
public class Application {

    public Application(DemoBean demoBean, MsgPublisher msgPublisher) {
        System.out.println("init: " + demoBean.getNum());
        msgPublisher.publish("一灰灰blog");
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
