package com.git.hui.boot.web.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by @author yihui in 20:21 20/9/7.
 */
@Controller
@RequestMapping(path = "attr")
public class AttrRest {

    /**
     * 会在下面的req调用之前被执行，然后往model中塞属性
     *
     * @param attr
     * @param model
     */
    @ModelAttribute
    public void attr(@RequestParam(name = "user", required = false) String attr, Model model) {
        model.addAttribute("user", "一灰灰:" + attr);
    }

    /**
     * http://127.0.0.1:8080/attr/req?user=哈哈
     *
     * @return
     */
    @GetMapping(path = "req")
    public String req() {
        return "index";
    }


    /**
     * 下面这个从效果上来看，等同于上面两个方法
     *
     * @param user
     * @return
     */
    @GetMapping(path = "index")
    public String index(@RequestParam(name = "user") String user, Model model) {
        model.addAttribute("user", "index-" + user);
        return "index";
    }
}
