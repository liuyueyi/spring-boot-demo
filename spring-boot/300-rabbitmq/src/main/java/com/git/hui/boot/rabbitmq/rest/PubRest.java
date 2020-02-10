package com.git.hui.boot.rabbitmq.rest;

import com.git.hui.boot.rabbitmq.bean.PublishDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by @author yihui in 12:36 20/2/10.
 */
@RestController
public class PubRest {
    @Autowired
    private PublishDemo publishDemo;

    @GetMapping(path = {"", "/", "/publish"})
    public String publish(String name) {
        return publishDemo.publish2mq(name);
    }
}
