package com.git.hui.boot.web.rest;

import com.git.hui.boot.web.ano.Platform;
import com.git.hui.boot.web.constants.PlatformEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * debug -> org.springframework.web.servlet.handler.AbstractHandlerMethodMapping#lookupHandlerMethod(java.lang.String, javax.servlet.http.HttpServletRequest)
 * Created by @author yihui in 17:58 19/10/8.
 */
@RestController
@RequestMapping(path = "method")
public class DemoMethodRest {
    @Platform
    @GetMapping(path = "index")
    public String allIndex() {
        return "default index";
    }

    @Platform(PlatformEnum.PC)
    @GetMapping(path = "index")
    public String pcIndex() {
        return "pc index";
    }


    @Platform(PlatformEnum.APP)
    @GetMapping(path = "index")
    public String appIndex() {
        return "app index";
    }

    @Platform(PlatformEnum.WAP)
    @GetMapping(path = "index")
    public String wapIndex() {
        return "wap index";
    }

    @GetMapping(path = "hello")
    public String hello() {
        return "hello";
    }
}
