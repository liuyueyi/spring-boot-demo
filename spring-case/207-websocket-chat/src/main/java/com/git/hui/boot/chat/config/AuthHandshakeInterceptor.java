package com.git.hui.boot.chat.config;

import com.git.hui.boot.chat.rest.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.Cookie;
import java.util.Map;

/**
 * @author YiHui
 * @date 2023/11/23
 */
@Slf4j
public class AuthHandshakeInterceptor extends HttpSessionHandshakeInterceptor {
    @Autowired
    private UserService userService;

    /**
     * 握手前，进行用户身份校验识别,  继续握手返回true, 中断握手返回false. 通过attributes参数设置登录的用户信息
     *
     * @param request
     * @param response
     * @param wsHandler
     * @param attributes: 即对应的是Message中的 simpSessionAttributes 请求头
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("开始握手");
        if (request instanceof ServletServerHttpRequest) {
            for (Cookie cookie : ((ServletServerHttpRequest) request).getServletRequest().getCookies()) {
                if ("l-login".equalsIgnoreCase(cookie.getName())) {
                    String val = cookie.getValue();
                    String uname = userService.getUsername(val);
                    log.info("获取登录用户: {}", uname);
                    attributes.put("uname", uname);
                    break;
                }
            }
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        System.out.println("握手结束");
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
