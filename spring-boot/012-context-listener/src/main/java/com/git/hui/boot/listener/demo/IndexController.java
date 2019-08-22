package com.git.hui.boot.listener.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by @author yihui in 08:59 19/8/21.
 */
@RestController
public class IndexController {
    @Autowired
    private DemoBean demoBean;

    @GetMapping(path = {"/", "/index"})
    public String show() {
        return "ans: " + demoBean.getNum();
    }

}
