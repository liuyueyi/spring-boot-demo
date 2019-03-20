package com.git.hui.boot.web.demo;

import org.springframework.stereotype.Service;

/**
 * Created by @author yihui in 22:41 19/3/17.
 */
@Service
public class PrintServer {

    public void print() {
        System.out.println(System.currentTimeMillis());
    }

}
