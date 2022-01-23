package com.git.hui.boot.web.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.git.hui.boot.web.resolver.CamelAno;
import com.git.hui.boot.web.resolver.databinder.ParamName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by @author yihui in 11:41 20/5/25.
 */
@RestController
public class RestDemo {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class ViewDo {
        @JsonProperty("user_id")
        @ParamName("user_id")
        private Integer userId;
        @JsonProperty("user_name")
        @ParamName("user_name")
        private String userName;
    }

    @CamelAno
    @GetMapping(path = "getV1")
    public ViewDo get(ViewDo view) {
        System.out.println(view);
        return view;
    }

    @CamelAno
    @GetMapping(path = "getV2")
    public ViewDo getV2(Integer userId, String userName) {
        String str = "userId: " + userId + " userName: " + userName;
        System.out.println(str);
        return new ViewDo(userId, userName);
    }

    @CamelAno
    @GetMapping(path = "getV3")
    public ViewDo getV3(@RequestParam("userId") Integer userId, @RequestParam("userName") String userName) {
        String str = "userId: " + userId + " userName: " + userName;
        System.out.println(str);
        return new ViewDo(userId, userName);
    }

    /**
     * http://127.0.0.1:8080/getV4?user_id=12&user_name=一灰灰
     * <p>
     * 传参是Map, 对应的Spring中参数转换是 MapMethodProcessor
     *
     * @param map
     * @return
     */
    @CamelAno
    @GetMapping(path = "getV4")
    public Map getV4(@RequestParam Map<String, Object> map) {
        System.out.println(map);
        return map;
    }

    /**
     * POJO 对应Spring中的参数转换是 ServletModelAttributeMethodProcessor | RequestParamMethodArgumentResolver
     *
     * @param viewDo
     * @return
     */
    @GetMapping(path = "getV5")
    public ViewDo getV5(ViewDo viewDo) {
        System.out.println("v5: " + viewDo);
        return viewDo;
    }

    /**
     * curl 'http://127.0.0.1:8080/postV1' -X POST -d 'user_id=123&user_name=一灰灰'
     * 注意：非json传参，jackson的配置将不会生效，即上面这个请求是不会实现下划线转驼峰的； 但是返回结果会是下划线的
     *
     * @param viewDo
     * @return
     */
    @PostMapping(path = "postV1")
    public ViewDo post(ViewDo viewDo) {
        System.out.println(viewDo);
        return viewDo;
    }

    /**
     * post json串
     * curl 'http://127.0.0.1:8080/postV2' -X POST -H 'content-type:application/json' -d '{"user_id": 123, "user_name": "一灰灰"}'
     *
     * @param viewDo
     * @return
     */
    @PostMapping(path = "postV2")
    public ViewDo postV2(@RequestBody ViewDo viewDo) {
        System.out.println(viewDo);
        return viewDo;
    }


    @GetMapping(path = "ano")
    public ViewDo ano(@ParamName("uesr_name") String userName, @ParamName("user_id") String userId) {
        ViewDo viewDo = new ViewDo(userName, userId);
        System.out.println(viewDo);
        return viewDo;
    }
}
