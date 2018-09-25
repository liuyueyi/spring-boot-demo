package com.git.hui.cloud.config.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by @author yihui in 09:31 18/9/12.
 */
@Controller
public class WebViewController {
    @RequestMapping(path = {"/", "/index"})
    public ModelAndView index() {
        return new ModelAndView("newindex");
    }
}
