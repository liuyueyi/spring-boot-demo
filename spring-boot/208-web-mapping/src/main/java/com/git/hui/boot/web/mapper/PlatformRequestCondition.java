package com.git.hui.boot.web.mapper;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by @author yihui in 18:01 19/10/8.
 */
public class PlatformRequestCondition implements RequestCondition<PlatformRequestCondition> {
    public static Map<String, Integer> enable;

    static {
        enable = new HashMap<>(4);
        enable.put("pc", 1);
        enable.put("wap", 1);
        enable.put("app", 1);
        enable.put("all", 0);
    }

    @Getter
    @Setter
    private String platform;

    public PlatformRequestCondition(String platform) {
        this.platform = platform;
    }

    @Override
    public PlatformRequestCondition combine(PlatformRequestCondition other) {
        return new PlatformRequestCondition(other.platform);
    }

    @Override
    public PlatformRequestCondition getMatchingCondition(HttpServletRequest request) {
        String platform = (String) request.getAttribute("x-platform");
        if (this.platform.equalsIgnoreCase(platform)) {
            return this;
        }

        return null;
    }

    /**
     * 优先级
     *
     * @param other
     * @param request
     * @return
     */
    @Override
    public int compareTo(PlatformRequestCondition other, HttpServletRequest request) {
        int thisOrder = enable.get(this.platform);
        int otherOrder = enable.get(other.platform);
        return otherOrder - thisOrder;
    }
}
