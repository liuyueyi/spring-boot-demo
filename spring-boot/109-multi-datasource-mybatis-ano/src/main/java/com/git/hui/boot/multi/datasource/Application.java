package com.git.hui.boot.multi.datasource;

import com.git.hui.boot.multi.datasource.repository.StoryMoneyRepository;
import com.git.hui.boot.multi.datasource.repository.TestMoneyRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yihui
 * @date 20/12/27
 */
@SpringBootApplication
public class Application {

    public Application(StoryMoneyRepository storyMoneyRepository, TestMoneyRepository testMoneyRepository) {
        storyMoneyRepository.query();
        testMoneyRepository.query();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
