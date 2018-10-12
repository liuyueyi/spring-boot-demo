package com.git.hui.cloud.config.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by @author yihui in 22:05 18/9/3.
 */
@EnableConfigServer
@SpringBootApplication
public class Application implements CommandLineRunner {

    public Application(DiscoveryClient discoveryClient) {
        List<String> list = discoveryClient.getServices();
        System.out.println(list);
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);

        List<String> newArgs = new ArrayList<>(Arrays.asList(args));
        newArgs.add("aConf");
        newArgs.add("cConf");
        newArgs.add("bConf");
        app.run(newArgs.toArray(new String[]{}));
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
