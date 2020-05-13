package com.git.hui.cloud.eurka.consumer.controller;

import com.alibaba.fastjson.JSONObject;
import com.git.hui.cloud.api.eurka.api.UserService;
import com.git.hui.cloud.api.eurka.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by @author yihui in 08:16 18/9/2.
 */
@Slf4j
@RestController
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    //    @Value("${basic.key}")
    private String hello;

    /**
     * 实例化RestTemplate
     *
     * LoadBalanced 这个注解不能少，否则走的RestTemplate不会被代理，直接访问对应url数据
     *
     * @return
     */
    @LoadBalanced
    @Bean
    public RestTemplate rest() {
        return new RestTemplate();
    }

    @GetMapping(path = "uid")
    public String getUser(int userId) {
        UserDTO dto = restTemplate
                .getForObject("http://eureka-service-provider/userService/getUserById?userId=" + userId, UserDTO.class);

        return userId + "'s info: " + dto;
    }

    @RequestMapping(path = "user")
    public String helloWord(@RequestParam int userId) {
        UserDTO data = restTemplate
                .getForObject("http://eureka-service-provider/userService/getUserById?userId=" + userId, UserDTO.class);

        UserDTO dto = userService.getUserById(userId);

        JSONObject ans = new JSONObject();
        ans.put("rest", data);
        ans.put("dto", dto);
        return ans.toString();
    }

    @RequestMapping(path = "up/txt")
    public String upload(@RequestParam String txt) throws IOException {
        InputStream file = this.getClass().getClassLoader().getResourceAsStream("32.kd");
        byte[] bytes = new byte[file.available()];
        file.read(bytes);
        file.close();
        String ans = userService.parser(bytes);
        return "upload return txt " + txt + " ->" + ans;
    }


    @CrossOrigin("*")
    @PostMapping(path = "up/file")
    public String upload(@RequestPart("file") MultipartFile file) {
        // 文件上传
        String ans = userService.upload(file);
        System.out.println(ans);
        return "success!";
    }
}
