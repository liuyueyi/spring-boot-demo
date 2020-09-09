package com.git.hui.boot.web.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by @author yihui in 20:21 20/9/7.
 */
@Controller
@RequestMapping(path = "attr4")
public class AttrRest4 {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserDo {
        private String user;
        private Integer age;
    }

    /**
     * 会在下面的req调用之前被执行，直接返回一个POJO，这个时候Model的属性名为 userDo
     *
     * @param user
     * @param age
     */
    @ModelAttribute(value = "userDo")
    public UserDo attr(String user, Integer age) {
        return new UserDo("一灰灰:" + user, age);
    }

    /**
     * 请注意，下面这个参数绑定的 userDo 会覆盖上面的
     *
     * http://127.0.0.1:8080/attr/req?user=一灰灰&age=18
     *
     * @return
     */
    @GetMapping(path = "req")
    public String req(@ModelAttribute UserDo user, Model model) {
        System.out.println("attribute: " + model.getAttribute("userDo"));
        return "index2";
    }

}
