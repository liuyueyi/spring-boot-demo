package com.git.hui.boot.order;

import com.git.hui.boot.client.EnableOrderClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by @author yihui in 11:39 20/2/28.
 */
@EnableOrderClient
@SpringBootApplication
public class Application {

    public Application(DemoBean demoBean) {
        demoBean.print();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
