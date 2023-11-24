package com.git.hui.boot.chat.rest;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author YiHui
 * @date 2023/11/23
 */
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
}
