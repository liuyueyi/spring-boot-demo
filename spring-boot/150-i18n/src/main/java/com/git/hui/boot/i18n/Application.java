package com.git.hui.boot.i18n;

import com.alibaba.fastjson.JSON;
import com.git.hui.boot.i18n.config.MsgUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * 国际化测试
 *
 * @author wuzebang
 * @date 2021/4/30
 */
@Controller
@SpringBootApplication
public class Application {

    public Application(ResourceBundleMessageSource messageSource) {
        MsgUtil.inti(messageSource);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Data
    @Accessors(chain = true)
    public static class RspWrapper<T> {
        private int code;
        private String msg;
        private T data;
    }

    @GetMapping(path = "change")
    @ResponseBody
    public String changeLocal(HttpServletRequest request, HttpServletResponse response) {
        String target = request.getParameter("language");
        String[] s = target.split("_");
        LocaleContextHolder.setLocale(new Locale(s[0], s[1]));
        RspWrapper res = new RspWrapper<>().setCode(200).setMsg(MsgUtil.get("200")).setData(true);
        return JSON.toJSONString(res);
    }

    @GetMapping(path = "say")
    @ResponseBody
    public String say(String name) {
        RspWrapper res = new RspWrapper<>().setCode(200).setMsg(MsgUtil.get("200")).setData(MsgUtil.get("name") + ":" + name);
        return JSON.toJSONString(res);
    }

    @GetMapping(path = {"", "/", "/index"})
    public String index(Model model) {
        model.addAttribute("name", MsgUtil.get("name"));
        model.addAttribute("pwd", MsgUtil.get("pwd"));
        return "index";
    }
}
