package com.git.hui.boot.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by @author yihui in 22:01 19/2/12.
 */
@SpringBootApplication
public class Application {

    public Application(RestTemplate restTemplate) {
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(args.length + 1);
        Collections.addAll(list, args);
        // 模拟命令行参数传入，覆盖默认的配置场景
        list.add("--server.port=8082");
        args = list.toArray(new String[]{});

        SpringApplication.run(Application.class, args);
    }
}
