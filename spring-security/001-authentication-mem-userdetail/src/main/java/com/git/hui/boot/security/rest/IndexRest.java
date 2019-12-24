package com.git.hui.boot.security.rest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by @author yihui in 20:30 19/12/22.
 */
@RestController
public class IndexRest {

    public String getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userName;
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    /**
     * @return
     */
    @GetMapping(path = {"/"})
    public String index() {
        return "hello this is index! welcome " + getUser();
    }
}
