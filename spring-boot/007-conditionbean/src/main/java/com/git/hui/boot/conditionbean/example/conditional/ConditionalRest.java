package com.git.hui.boot.conditionbean.example.conditional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by @author yihui in 22:14 18/10/17.
 */
@RestController
@RequestMapping(path = "/conditional")
public class ConditionalRest {
    @Autowired
    private Environment environment;

    @Autowired
    private RandDataComponent randDataComponent;

    @Autowired(required = false)
    private ScanDemoBean scanDemoBean;

    @GetMapping(path = "/show")
    public String show() {
        String type = environment.getProperty("conditional.rand.type");
        return randDataComponent.rand() + " >>> " + type;
    }

    @GetMapping(path = "/scan")
    public String showDemo() {
        String type = environment.getProperty("conditional.demo.load");
        if (scanDemoBean == null) {
            return "not exists! >>>" + type;
        } else {
            return "load : " + scanDemoBean.getLoad() + " >>>" + type;
        }
    }
}
