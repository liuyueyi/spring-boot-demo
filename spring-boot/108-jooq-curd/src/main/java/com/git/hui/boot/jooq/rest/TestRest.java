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
    private PoetryGroupQueryRepository poetryGroupQueryRepository;

    @Autowired
    private PoetryFunctionQueryRepository poetryFunctionQueryRepository;

    @Autowired
    private PoetryJoinerRepository poetryJoinerRepository;

    @Autowired
    private PoetryTransactionRepository poetryTransactionRepository;

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

    @GetMapping(path = "group")
    public String groupQuery() {
        poetryGroupQueryRepository.testGroup();
        return "over";
    }

    @GetMapping(path = "query")
    public String query() {
        poetQueryRepository.test();
        return "over";
    }

    @GetMapping(path = "func")
    public String functionQuery() {
        poetryFunctionQueryRepository.test();
        return "over";
    }

    @GetMapping(path = "joiner")
    public String joinerQuery() {
        poetryJoinerRepository.test();
        return "over";
    }

    @GetMapping(path = "trans")
    public String transaction() {
        try {
            poetryTransactionRepository.transaction();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            poetryTransactionRepository.trans2();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return "over";
    }
}