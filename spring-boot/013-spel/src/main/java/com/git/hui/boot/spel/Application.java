package com.git.hui.boot.spel;

import com.git.hui.boot.spel.demo.BasicSpelDemo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by @author yihui in 14:29 20/5/14.
 */
@SpringBootApplication
public class Application {

    public Application(BasicSpelDemo demo) {
        demo.test();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
