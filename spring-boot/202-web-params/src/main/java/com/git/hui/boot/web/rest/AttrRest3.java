package com.git.hui.boot.web.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by @author yihui in 20:21 20/9/7.
 */
@Controller
@RequestMapping(path = "attr3")
public class AttrRest3 {


    /**
     * 会在下面的req调用之前被执行，注意属性名直接在注解中指定了
     *
     * @param user
     */
    @ModelAttribute(value = "user")
    public String attr(String user) {
        return "一灰灰:" + user;
    }

    /**
     * http://127.0.0.1:8080/attr3/req?user=一灰灰
     *
     * @return
     */
    @GetMapping(path = "req")
    public String req() {
        return "index";
    }

}
