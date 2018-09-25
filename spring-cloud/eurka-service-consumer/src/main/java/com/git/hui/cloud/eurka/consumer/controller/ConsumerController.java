package com.git.hui.cloud.eurka.consumer.controller;

import com.alibaba.fastjson.JSONObject;
import com.git.hui.cloud.api.eurka.dto.UserDTO;
import com.git.hui.cloud.eurka.consumer.rpc.UserServiceRpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by @author yihui in 08:16 18/9/2.
 */
@Slf4j
@RestController
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserServiceRpc userServiceRpc;

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

    @RequestMapping(path = "user")
    public String helloWord(@RequestParam int userId) {
        UserDTO data = restTemplate
                .getForObject("http://eurka-service-provider/userService/getUserById?userId=" + userId, UserDTO.class);

        UserDTO dto = userServiceRpc.getUserById(userId);

        JSONObject ans = new JSONObject();
        ans.put("rest", data);
        ans.put("dto", dto);
        return ans.toString();
    }

}
