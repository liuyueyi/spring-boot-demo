package com.git.hui.boot.chat.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @author YiHui
 * @date 2023/11/23
 */
@Controller
public class ChatController {
    @Autowired
    private UserService userService;

    @GetMapping(path = "/")
    public String index(Model model) {
        model.addAttribute("uname", userService.getUsernameByCookie());
        return "index";
    }

    @GetMapping(path = "/view/{chat}")
    public String chat(@PathVariable String chat, Model modelAttribute) {
        modelAttribute.addAttribute("uname", userService.getUsernameByCookie());
        return chat;
    }

    private static final int COOKIE_AGE = 30 * 86400;

    public static Cookie newCookie(String key, String session) {
        return newCookie(key, session, "/", COOKIE_AGE);
    }

    public static Cookie newCookie(String key, String session, String path, int maxAge) {
        Cookie cookie = new Cookie(key, session);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        return cookie;
    }

    @GetMapping(path = "login")
    @ResponseBody
    public String login(String name, HttpServletResponse response) {
        String sessionId = userService.login(name);
        response.addCookie(newCookie("l-login", sessionId));
        return sessionId;
    }

    /**
     * 当接受到客户端发送的消息时, 发送的路径是： /app/hello (这个/app前缀是 StompConfiguration 中的配置的)
     * 将返回结果推送给所有订阅了 /topic/chat/channel 的消费者
     *
     * @param content
     * @return
     */
    @MessageMapping("/hello/{channel}")
    public void sayHello(String content, @DestinationVariable("channel") String channel, SimpMessageHeaderAccessor headerAccessor) {
        String text = String.format("【%s】发送内容：%s", headerAccessor.getSessionAttributes().get("uname"), content);
        WsAnswerHelper.publish("/topic/chat/" + channel, text);
    }


    /**
     * 富文本传输的支持；将图文base64之后传递给后端
     */
    @MessageMapping("/msg/{channel}")
    public void sendFile(Message msg, @DestinationVariable("channel") String channel) {
        Object payload = msg.getPayload();
        if (payload instanceof byte[]) {
            payload = new String((byte[]) payload);
        }

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(msg);
        String uname = headerAccessor.getUser().getName();
        HashMap map = new HashMap();
        map.put("uname", uname);
        map.put("time", System.currentTimeMillis());
        map.put("msg", payload);
        WsAnswerHelper.publish("/topic/chat/" + channel, map);
    }
}
