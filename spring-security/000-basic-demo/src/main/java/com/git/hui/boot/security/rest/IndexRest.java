package com.git.hui.boot.security.rest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Created by @author yihui in 20:30 19/12/22.
 */
@RestController
public class IndexRest {

    public String getUser() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getRemoteUser();
    }

    public String getUserV2() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userName;
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    @GetMapping(path = {"/", "/index"})
    public String index() {
        return "hello this is index! welcome " + getUser();
    }

    @GetMapping(path = "hello")
    public String hello(String name) {
        return getUserV2() + " welcome " + name;
    }

}
