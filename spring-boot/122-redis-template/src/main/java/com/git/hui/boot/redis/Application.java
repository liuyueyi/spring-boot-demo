package com.git.hui.boot.redis;

import com.git.hui.boot.redis.demo.PipelineBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by @author yihui in 10:10 18/11/6.
 */
@SpringBootApplication
public class Application {

//    public Application(PipelineBean pipelineBean) {
//        pipelineBean.counter("tt", "123", "456");
//        pipelineBean.counter("tt", "122", "456");
//        pipelineBean.counter("tt", "123", "456");
//    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
