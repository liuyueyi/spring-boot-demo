package com.git.hui.boot.spi;

import com.git.hui.boot.spi.demo.print.IPrint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by @author yihui in 16:07 18/10/23.
 */
@SpringBootApplication
public class Application {

    public Application(IPrint printProxy) {
        printProxy.execute(10, " log print ");
        printProxy.execute(0, " console print ");
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
