package com.git.hui.boot.webflux.action;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by @author yihui in 14:26 20/6/11.
 */
@Controller
public class ViewAction {

    @GetMapping(path = "a", produces = MediaType.TEXT_HTML_VALUE)
    public String a() {
        return "a.html";
    }
}
