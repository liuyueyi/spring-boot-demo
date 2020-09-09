package com.git.hui.boot.web.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by @author yihui in 20:21 20/9/7.
 */
@Controller
@RequestMapping(path = "attr5")
public class AttrRest5 {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserDo {
        private String user;
        private Integer age;
    }


    /**
     * 直接将url参数绑定到视图
     *
     * http://127.0.0.1:8080/attr5/req?user=一灰灰&age=18
     *
     * @return
     */
    @GetMapping(path = "req")
    public String req(@ModelAttribute UserDo userDo) {
        System.out.println("modelAttribute: " + userDo);
        return "index2";
    }
}
