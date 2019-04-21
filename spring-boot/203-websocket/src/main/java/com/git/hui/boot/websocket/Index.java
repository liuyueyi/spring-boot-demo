package com.git.hui.boot.websocket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by @author yihui in 10:59 19/4/18.
 */
@Controller
public class Index {

    @RequestMapping(path = {"", "/", "/index"})
    public String index() {
        return "index";
    }

}
