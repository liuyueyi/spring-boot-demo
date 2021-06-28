package com.git.hui.boot.zk;

import com.git.hui.boot.zk.example.NodeExample;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

/**
 * @author yihui
 * @date 2021/4/14
 */
@SpringBootApplication
public class Application {
    public Application(NodeExample nodeExample) {
        nodeExample.test();

        Scanner reader = new Scanner(System.in);
        String ans = reader.next();
        System.out.println("over->" + ans);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
