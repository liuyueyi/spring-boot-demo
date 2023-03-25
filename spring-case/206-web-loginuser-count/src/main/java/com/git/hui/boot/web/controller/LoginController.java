package com.git.hui.boot.web.controller;

import com.git.hui.boot.web.service.CountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * <a href="https://www.cnblogs.com/binger/archive/2013/03/19/2970171.html">session生命周期</a>
 * <p>
 * session：再浏览器窗口打开期间，这个会话一直有效，即先访问login，然后再访问time，可以直接拿到name， 若再此过程中，再次访问了login更新了name，那么访问time获取到的也是新的name
 * <p>
 * 当浏览器关闭之后，重新再访问 time 接口，则此时将拿不到 name
 * <p>
 * session的核心工作原理：
 * - 借助cookie中的 JESSIONID 来作为用户身份标识，这个数据相同的，认为是同一个用户；然后会将session再内存中存一份，有过期时间的限制，通常每次访问一次，过期时间重新刷新
 * - 当浏览器不支持cookie时，借助url重写，将 sessionId 写道url的地址中，参数名 = jsessionid
 *
 * @author YiHui
 * @date 2023/3/16
 */
@RestController
public class LoginController {
    @Autowired
    private CountService countService;

    @RequestMapping(path = "/login")
    public String login(String uname, HttpSession httpSession) {
        httpSession.setAttribute("name", uname);
        System.out.println("登录成功:" + uname);
        return "欢迎登录：" + uname + "， 当前在线人数: " + countService.getOnlineCnt();
    }


    /**
     * 查询当前在线人数
     *
     * @param session
     * @return
     */
    @RequestMapping("online")
    public String showOnlineUser(HttpSession session) {
        return session.getAttribute("name") + " ，当前时间为：" + LocalDateTime.now() + " 在线人数：" + countService.getOnlineCnt();
    }

    @RequestMapping(path = "logout")
    public String logout(HttpSession httpSession) {
        // 注销当前的session
        httpSession.invalidate();
        return "登出成功， 当前在线人数: " + countService.getOnlineCnt();
    }
}
