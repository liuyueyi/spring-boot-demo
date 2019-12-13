package com.git.hui.boot.importbean;

import com.git.hui.boot.importbean.anno.MetaComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by @author yihui in 11:56 19/12/13.
 */
@SpringBootApplication
@MetaComponentScan
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
