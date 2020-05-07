package com.git.hui.boot.dconfig.demo.bean;

import com.git.hui.boot.dynamic.config.anno.MetaVal;
import com.git.hui.boot.dynamic.config.parser.MetaParser;
import org.springframework.stereotype.Component;

/**
 * Created by @author yihui in 21:58 20/4/21.
 */
@Component
public class DemoBean {

    @MetaVal("name")
    private String name;

    @MetaVal("blog")
    private String blog;

    @MetaVal(value = "age", parser = MetaParser.INT_PARSER)
    private Integer age;

    public String sayHello() {
        return "欢迎关注 [" + name + "] 博客：" + blog + " | " + age;
    }

}
