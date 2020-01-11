package com.git.hui.boot.security.rest.rbac;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Created by @author yihui in 14:54 19/12/24.
 */
@RestController
@RequestMapping(path = "/rbac/manager")
public class ManagerRest {

    @GetMapping(path = "gen")
    public String gen() {
        return "manager:" + StringUtils.replace(UUID.randomUUID().toString(), "-", "");
    }
}
