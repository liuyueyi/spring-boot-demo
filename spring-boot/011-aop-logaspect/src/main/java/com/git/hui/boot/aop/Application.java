package com.git.hui.boot.aop;

import com.git.hui.boot.aop.anodemo.AnoDemo;
import com.git.hui.boot.aop.demo.PrintDemo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by @author yihui in 17:58 19/3/13.
 */
@SpringBootApplication
public class Application {

    public Application(PrintDemo printDemo, AnoDemo anoDemo) {
        System.out.println(printDemo.genRand(10, "--一灰灰Blog"));
        System.out.println(anoDemo.gen("!23"));
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
