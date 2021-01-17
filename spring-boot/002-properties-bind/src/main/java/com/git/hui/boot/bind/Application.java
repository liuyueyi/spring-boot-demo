package com.git.hui.boot.bind;

import com.git.hui.boot.bind.config.BindConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yihui
 * @date 21/1/16
 */
@SpringBootApplication
public class Application {

    public Application(BindConfig config) {
        System.out.println(config);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
