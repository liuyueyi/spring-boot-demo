package com.git.hui.boot.security.rest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by @author yihui in 15:56 19/12/24.
 */
@RestController
public class IndexRest {

    @GetMapping(path = "/")
    public String index() {
        return "欢迎登陆: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
