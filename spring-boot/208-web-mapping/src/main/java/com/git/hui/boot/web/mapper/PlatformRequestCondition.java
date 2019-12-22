package com.git.hui.boot.web.mapper;

import com.git.hui.boot.web.constants.PlatformEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by @author yihui in 18:01 19/10/8.
 */
public class PlatformRequestCondition implements RequestCondition<PlatformRequestCondition> {
    @Getter
    @Setter
    private PlatformEnum platform;

    public PlatformRequestCondition(PlatformEnum platform) {
        this.platform = platform;
    }

    @Override
    public PlatformRequestCondition combine(PlatformRequestCondition other) {
        // 采用最后定义优先原则，则方法上的定义覆盖类上面的定义
        return new PlatformRequestCondition(other.platform);
    }

    @Override
    public PlatformRequestCondition getMatchingCondition(HttpServletRequest request) {
        PlatformEnum platform = this.getPlatform(request);
        if (this.platform.equals(platform)) {
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
        int thisOrder = this.platform.getOrder();
        int otherOrder = other.platform.getOrder();
        return otherOrder - thisOrder;
    }


    private PlatformEnum getPlatform(HttpServletRequest request) {
        String platform = request.getHeader("x-platform");
        return PlatformEnum.nameOf(platform);
    }
}
