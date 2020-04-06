package com.git.hui.boot.mybatis.plus;

import com.git.hui.boot.mybatis.plus.entity.UserT0;
import com.git.hui.boot.mybatis.plus.service.IUserT0Service;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by @author yihui in 13:04 20/4/6.
 */
@RestController
@SpringBootApplication
@MapperScan("com.git.hui.boot.mybatis.plus.mapper")
public class Application {
    @Autowired
    private IUserT0Service userT0Service;

    @GetMapping
    public UserT0 hello(int id) {
        return userT0Service.getById(id);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
