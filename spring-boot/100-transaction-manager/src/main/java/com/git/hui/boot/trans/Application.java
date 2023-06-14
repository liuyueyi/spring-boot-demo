package com.git.hui.boot.trans;

import com.git.hui.boot.trans.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YiHui
 * @date 2023/6/14
 */
@RestController
@SpringBootApplication
public class Application {
    @Autowired
    private DemoService demoService;

    @GetMapping(path = "/")
    public String preExecute() {
        try {
            demoService.transExecute(true);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "ok";
    }

    @GetMapping(path = "/out")
    public String outExecute() {
        try {
            demoService.outTransExecute();
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "ok";
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
