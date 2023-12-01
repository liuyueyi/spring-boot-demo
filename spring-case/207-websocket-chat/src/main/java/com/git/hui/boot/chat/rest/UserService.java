package com.git.hui.boot.chat.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author YiHui
 * @date 2023/11/23
 */
@Slf4j
@Service
public class UserService {
    private Map<String, String> userCache;

    @PostConstruct
    public void init() {
        userCache = new HashMap<>();
    }

    public String login(String uname) {
        return userCache.computeIfAbsent(uname, s -> UUID.randomUUID().toString());
    }

    public String getUsername(String session) {
        for (Map.Entry<String, String> entry : userCache.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(session)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public String getUsernameByCookie() {
        ServletRequestAttributes requestAttr = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        if (requestAttr != null) {
            HttpServletRequest request = requestAttr.getRequest();
            if (request.getCookies() == null) {
                return null;
            }

            Cookie ck = Arrays.stream(request.getCookies()).filter(s -> s.getName().equalsIgnoreCase("l-login")).findAny().orElse(null);
            if (ck != null) {
                return getUsername(ck.getValue());
            }
        }
        return null;
    }

    @Scheduled(fixedRate = 3000)
    public void autoSendMsgToUser() {
        userCache.keySet().forEach(uname -> {
            log.info("用户广播消息: {}", uname);
            WsAnswerHelper.publish(uname, "/topic/notify", String.format("【%s】当前时间: %s", uname, LocalDateTime.now()));
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
