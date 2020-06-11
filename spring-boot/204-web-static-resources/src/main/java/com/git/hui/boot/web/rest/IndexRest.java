package com.git.hui.boot.web.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 资源路径，默认依次从  META-INF/resources > resource > static > public 中查找对应的静态文件
 *
 * Created by @author yihui in 08:44 20/6/10.
 */
@Controller
public class IndexRest {

    @GetMapping(path = "index")
    public String index() {
        return "index.html";
    }

    @GetMapping(path = "m")
    public String m() {
        return "m.html";
    }

    @GetMapping(path = "r")
    public String r() {
        return "r.html";
    }

    @GetMapping(path = "s")
    public String s() {
        return "s.html";
    }

    @GetMapping(path = "p")
    public String p() {
        return "p.html";
    }

    @GetMapping(path = "o")
    public String v() {
        return "o.html";
    }
}
