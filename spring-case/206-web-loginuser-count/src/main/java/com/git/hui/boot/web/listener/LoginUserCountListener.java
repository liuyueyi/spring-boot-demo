package com.git.hui.boot.web.listener;

import com.git.hui.boot.web.service.CountService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author YiHui
 * @date 2023/3/25
 */
@WebListener
public class LoginUserCountListener implements HttpSessionListener {
    @Autowired
    private CountService countService;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("--------- 新增一个用户 ------- session = " + se.getSession().getId());
        HttpSessionListener.super.sessionCreated(se);
        countService.incr(1);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("--------- 销毁一个用户 -----------" + se.getSession().getId() + " = " + se.getSession().getAttribute("name"));
        HttpSessionListener.super.sessionDestroyed(se);
        countService.incr(-1);
    }
}
