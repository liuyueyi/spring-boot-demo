package com.git.hui.web.port;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author YiHui
 * @date 2023/4/29
 */
@SpringBootApplication
public class Application {

    private static int getPort() {
        return (int) (8080 + Math.random() * 10);
    }

    @Bean
    public TomcatConnectorCustomizer customServerPortTomcatConnectorCustomizer() {
        // 方式1. 设置tomcat的端口号
        int port = getPort();
        System.out.println("使用的端口号为： " + port);
        return connector -> connector.setPort(port);
    }

    public static void main(String[] args) {
        // 方式2
//        List<String> list = new ArrayList<>(args.length + 1);
//        Collections.addAll(list, args);
//        // 模拟命令行参数传入，覆盖默认的配置场景
//        list.add("--server.port=" + getPort());
//        args = list.toArray(new String[]{});

        SpringApplication.run(Application.class, args);
    }
}
