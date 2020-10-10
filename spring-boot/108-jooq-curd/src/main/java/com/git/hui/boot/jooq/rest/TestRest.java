package com.git.hui.boot.jooq.rest;

import com.git.hui.boot.jooq.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by @author yihui in 10:42 20/9/16.
 */
@RestController
public class TestRest {
    @Autowired
    private PoetCreateRepository poetCreateRepository;

    @Autowired
    private PoetUpdateRepository poetUpdateRepository;

    @Autowired
    private PoetRemoveRepository poetRemoveRepository;

    @Autowired
    private PoetQueryRepository poetQueryRepository;

    @Autowired
    private PoetryFunctionQueryRepository poetryFunctionQueryRepository;

    @GetMapping(path = "add")
    public String add() {
        poetCreateRepository.test();
        return "over";
    }

    @GetMapping(path = "update")
    public String update() {
        poetUpdateRepository.test();
        return "over";
    }

    @GetMapping(path = "remove")
    public String remove() {
        poetRemoveRepository.test();
        return "over";
    }

    @GetMapping(path = "func")
    public String functionQuery() {
        poetryFunctionQueryRepository.testGroup();
        return "over";
    }
}