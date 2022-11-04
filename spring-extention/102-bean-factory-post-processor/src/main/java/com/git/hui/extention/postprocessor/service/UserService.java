package com.git.hui.extention.postprocessor.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author YiHui
 * @date 2022/11/4
 */
@Service
@PropertySource("classpath:user.properties")
public class UserService {
    @Getter
    @Setter
    private String inject;
    @Value("${user}")
    private String user;
    @Value("${pwd}")
    private String pwd;

    @PostConstruct
    public void show() {
        System.out.println("user=" + user + " & pwd=" + pwd + " & inject=" + inject);
    }
}
