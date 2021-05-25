package com.git.hui.boot.aop;

import com.git.hui.boot.aop.anodemo.AnoDemo;
import com.git.hui.boot.aop.anodemo.BaseApi;
import com.git.hui.boot.aop.demo.PrintDemo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by @author yihui in 17:58 19/3/13.
 */
@SpringBootApplication
public class Application {

    public Application(PrintDemo printDemo, AnoDemo anoDemo, BaseApi baseApi) throws InterruptedException {
//        System.out.println(printDemo.genRand(10, "--一灰灰Blog"));
//        System.out.println(anoDemo.gen("!23"));
//        System.out.println("\n\n\n ----------- \n\n");

        for (int i = 0; i < 100; i++) {
            System.out.println(baseApi.print("hello world"));
            System.out.println("-----------\n\n");
        }

        System.out.println(baseApi.print2("hello world"));
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
