package com.git.hui.boot.reactor;

import com.git.hui.boot.reactor.basic.FluxDemo;
import com.git.hui.boot.reactor.basic.MonoDemo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by @author yihui in 18:20 20/5/27.
 */
@SpringBootApplication
public class Application {

    public Application(MonoDemo monoDemo, FluxDemo fluxDemo) {
//        monoDemo.basicTest();
        fluxDemo.basicTest();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
