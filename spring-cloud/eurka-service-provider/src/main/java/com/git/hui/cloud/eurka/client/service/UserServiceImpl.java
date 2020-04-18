package com.git.hui.cloud.eurka.client.service;

import com.git.hui.cloud.api.eurka.api.UserService;
import com.git.hui.cloud.api.eurka.dto.UserDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by @author yihui in 08:08 18/9/2.
 */
@RestController
@RequestMapping(path = "userService")
public class UserServiceImpl implements UserService {
    @Override
    @RequestMapping(path = "/getUserById")
    public UserDTO getUserById(@RequestParam long userId) {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userId);
        userDTO.setNickname("一灰灰blog");
        userDTO.setUserName("yihuihuiblog");
        userDTO.setPhone(88888888L);
        return userDTO;
    }
}
