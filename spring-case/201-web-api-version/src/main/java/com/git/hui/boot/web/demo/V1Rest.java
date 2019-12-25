package com.git.hui.boot.web.demo;

import com.git.hui.boot.web.annotation.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by @author yihui in 18:05 19/12/24.
 */
@RestController
@RequestMapping(path = "v1")
public class V1Rest {

    @GetMapping(path = "show")
    public String show1() {
        return "v1/show 1.0.0";
    }

    @Api("1.1.0")
    @GetMapping(path = "show")
    public String show2() {
        return "v1/show 1.1.0";
    }

    @Api("1.1.2")
    @GetMapping(path = "show")
    public String show3() {
        return "v1/show 1.1.2";
    }
}
