package com.git.hui.boot.h2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Created by @author yihui in 08:58 20/9/9.
 */
@RestController
public class TestController {
    @Autowired
    private TestRepository testRepository;

    @GetMapping("/save")
    public TestEntity save(Integer id, String name) {
        TestEntity testEntity = new TestEntity();
        testEntity.setId(id);
        testEntity.setName(name);
        return testRepository.save(testEntity);
    }

    @GetMapping("/update")
    public TestEntity update(Integer id, String name) {
        Optional<TestEntity> entity = testRepository.findById(id);
        TestEntity testEntity = entity.get();
        testEntity.setName(name);
        return testRepository.save(testEntity);
    }

    @GetMapping("/list")
    public Iterable list() {
        return testRepository.findAll();
    }

    @GetMapping("/get")
    public TestEntity get(Integer id) {
        return testRepository.findById(id).get();
    }

    @GetMapping("/del")
    public boolean del(Integer id) {
        testRepository.deleteById(id);
        return true;
    }
}
