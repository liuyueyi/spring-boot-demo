package com.git.hui.boot.security.rest.rbac;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Created by @author yihui in 14:52 19/12/24.
 */
@RestController
@RequestMapping(path = "/rbac/guest")
public class GuestRest {

    @GetMapping(path = "gen")
    public String gen() {
        return "guest:" + StringUtils.replace(UUID.randomUUID().toString(), "-", "");
    }

}
