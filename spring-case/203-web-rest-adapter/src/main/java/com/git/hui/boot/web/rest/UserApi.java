package com.git.hui.boot.web.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by @author yihui in 12:37 20/4/5.
 */
@RequestMapping(path = "UserApi")
public interface UserApi {

    @RequestMapping(path = "getName")
    String getName(@RequestParam(name = "id") int userId);

    @PostMapping(path = "updateName")
    String updateName(String user, int age);

}
