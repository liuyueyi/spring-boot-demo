package com.git.hui.boot.spel.demo;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 15:59 20/5/14.
 */
@Data
@Component
public class BeanDemo {

    private String blog = "https://spring.hhui.top";

    private Integer num = 8;

    public String hello(String name) {
        return "hello " + name + ", welcome to my blog  " + blog + ", now person: " + num;
    }
}
