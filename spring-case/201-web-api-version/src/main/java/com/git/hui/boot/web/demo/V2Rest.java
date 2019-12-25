package com.git.hui.boot.web.demo;

import com.git.hui.boot.web.annotation.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by @author yihui in 18:05 19/12/24.
 */
@Api("2.0.0")
@RestController
@RequestMapping(path = "v2")
public class V2Rest {

    @Api("1.1.0")
    @GetMapping(path = "show")
    public String show0() {
        return "v2/show0 1.1.0";
    }

    /**
     * 和show0 的响应冲突了
     *
     * @return
     */
    @GetMapping(path = "show")
    public String show1() {
        return "v2/show1 2.0.0";
    }

    @Api("2.1.1")
    @GetMapping(path = "show")
    public String show2() {
        return "v2/show2 2.1.1";
    }

    @Api("2.2.0")
    @GetMapping(path = "show")
    public String show3() {
        return "v2/show3 2.2.0";
    }
}
