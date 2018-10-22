package com.git.hui.boot.beanorder.choose.sameclz;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 18:22 18/10/22.
 */
@Component
public class ConsolePrint implements IPrint {

    @Override
    public void print(String msg) {
        System.out.println("console print: " + msg);
    }
}
