package com.git.hui.boot.spi.demo.print;

import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 16:41 18/10/23.
 */
@Component
public class ConsolePrint implements IPrint {
    @Override
    public void print(String msg) {
        System.out.println("console print: " + msg);
    }

    @Override
    public boolean verify(Integer condition) {
        return condition <= 0;
    }
}
