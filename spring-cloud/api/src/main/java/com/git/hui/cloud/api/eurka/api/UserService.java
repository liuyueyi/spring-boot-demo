package com.git.hui.cloud.api.eurka.api;

import com.git.hui.cloud.api.eurka.ApiConfiguration;
import com.git.hui.cloud.api.eurka.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by @author yihui in 08:07 18/9/2.
 */
@Component
@FeignClient(ApiConfiguration.SERVICE_NAME)
@RequestMapping(path = "userService")
public interface UserService {
    /**
     * feigin 普通调用
     *
     * @param userId
     * @return
     */
    @GetMapping(path = "getUserById")
    UserDTO getUserById(@RequestParam("userId") long userId);

    /**
     * 字节数组支持
     *
     * @param bytes
     * @return
     */
    @RequestMapping(path = "bytes", method = RequestMethod.POST)
    String parser(@RequestParam("bytes") byte[] bytes);

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @RequestMapping(path = "upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String upload(@RequestPart("file") MultipartFile file);
}
