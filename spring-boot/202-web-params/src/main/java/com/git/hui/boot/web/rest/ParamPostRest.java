package com.git.hui.boot.web.rest;

import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * Created by @author yihui in 18:48 19/8/23.
 */
@RestController
@RequestMapping(path = "post")
public class ParamPostRest {

    @PostMapping(path = "entity")
    public String entityParam(RequestEntity requestEntity) {
        return Objects.requireNonNull(requestEntity.getBody()).toString();
    }
}
