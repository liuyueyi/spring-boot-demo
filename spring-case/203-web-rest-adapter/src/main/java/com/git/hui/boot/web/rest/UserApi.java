package com.git.hui.boot.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by @author yihui in 12:37 20/4/5.
 */
@RestController
public interface UserApi {

    @GetMapping(path = "getName")
    String getName(@RequestParam(name = "id") int userId);

    @PostMapping(path = "updateName")
    String updateName(String user, int age);

}
