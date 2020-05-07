package com.git.hui.boot.dconfig.demo.action;

import com.git.hui.boot.dconfig.demo.bean.DemoBean;
import com.git.hui.boot.dconfig.demo.meta.MetaPropertyHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by @author yihui in 22:02 20/4/21.
 */
@RestController
public class DemoAction {

    @Autowired
    private DemoBean demoBean;

    @Autowired
    private MetaPropertyHolder metaPropertyHolder;

    @GetMapping(path = "hello")
    public String hello() {
        return demoBean.sayHello();
    }

    @GetMapping(path = "update")
    public String updateBlog(@RequestParam(name = "key") String key, @RequestParam(name = "val") String val,
            HttpServletResponse response) throws IOException {
        metaPropertyHolder.updateProperty(key, val);
        response.sendRedirect("/hello");
        return "over!";
    }
}
