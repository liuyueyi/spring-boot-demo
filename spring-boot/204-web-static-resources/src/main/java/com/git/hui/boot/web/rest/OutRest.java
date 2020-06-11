package com.git.hui.boot.web.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by @author yihui in 09:02 20/6/10.
 */
@Controller
public class OutRest {

    @GetMapping(path = "ui")
    public String ui() {
        return "ui.html";
    }

    @GetMapping(path = "out")
    public String out() {
        return "out.html";
    }

    // 第三方jar包的 META-INF/resources 优先级也高于当前包的 /static

    @GetMapping(path = "s2")
    public String s2() {
        return "s2.html";
    }

    @GetMapping(path = "ts/ts")
    public String ts2() {
        return "ts.html";
    }

    @GetMapping(path = "ts")
    public String ts() {
        return "ts.html";
    }
}
