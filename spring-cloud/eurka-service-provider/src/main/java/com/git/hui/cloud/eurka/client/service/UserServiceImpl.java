package com.git.hui.cloud.eurka.client.service;

import com.git.hui.cloud.api.eurka.api.UserService;
import com.git.hui.cloud.api.eurka.dto.UserDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

/**
 * Created by @author yihui in 08:08 18/9/2.
 */
@RestController
@RequestMapping(path = "userService")
public class UserServiceImpl implements UserService {

    private HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    @Override
    @RequestMapping(path = "/getUserById")
    public UserDTO getUserById(@RequestParam long userId) {
        HttpServletRequest attributes = getRequest();
        Enumeration<String> e = attributes.getHeaderNames();
        while (e.hasMoreElements()) {
            System.out.println("headers: " + attributes.getHeader(e.nextElement()));
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userId);
        userDTO.setNickname("一灰灰blog");
        userDTO.setUserName("yihuihuiblog");
        userDTO.setPhone(88888888L);
        return userDTO;
    }


    @Override
    @RequestMapping(path = "upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@RequestPart("file") MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            return "upload :" + new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("upload some error! ");
            return "error!";
        }
    }

    /**
     * 字节数组的上传
     *
     * @param bytes 2
     * @return
     * @throws UnsupportedEncodingException
     */
    @Override
    @RequestMapping(path = "bytes", method = RequestMethod.POST)
    public String parser(@RequestParam byte[] bytes) {
        try {
            String str = new String(bytes, "utf-8");
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("some error!");
            return "error!";
        }
    }
}
