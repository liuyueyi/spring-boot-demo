package com.git.hui.boot.web.rest;

import com.git.hui.boot.web.common.AdapterController;
import com.git.hui.boot.web.common.AdapterGet;
import com.git.hui.boot.web.common.AdapterPost;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by @author yihui in 10:41 20/4/5.
 */
@AdapterController
public class BasicRest {

    @Data
    public static class NameDTO {
        private String user;
        private Integer age;
    }

    /**
     * 访问姿势: curl -X POST 'http://127.0.0.1:8080/BasicRest/hello' -H 'content-type:application/json' -d '{"user": "yhh", "age": 18}'
     *
     * @param dto
     * @return
     */
    @AdapterPost
    public String hello(@RequestBody NameDTO dto) {
        return "hello : " + dto.toString();
    }

    /**
     * 访问姿势: curl -X POST 'http://127.0.0.1:8080/BasicRest/welcome' -d 'user=yhh&age=18'
     *
     * @param user
     * @param age
     * @return
     */
    @AdapterPost
    public String welcome(String user, int age) {
        return "welcome : " + user + " / " + age;
    }

    /**
     * 访问地址: /BasicRest/index
     *
     * @return
     */
    @AdapterGet
    public String index(String name) {
        return "index: " + name;
    }
}
