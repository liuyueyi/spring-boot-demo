package com.git.hui.test;

import com.git.hui.boot.redis.site.util.DateUtil;
import org.junit.Test;

import java.net.URI;

/**
 * Created by @author yihui in 18:01 19/5/12.
 */
public class DateTest {

    @Test
    public void testLocal() {
        System.out.println(DateUtil.getToday());
    }

    @Test
    public void testUri() {
        String uri = "http://spring.hhui.top/spring-blog/2018/12/25/181225-SpringBoot%E5%BA%94%E7%94%A8%E7%AF%87%E4" +
                "%B9%8B%E5%80%9F%E5%8A%A9Redis%E5%AE%9E%E7%8E%B0%E6%8E%92%E8%A1%8C%E6%A6%9C%E5%8A%9F%E8%83%BD/#4-%E8" +
                "%8E%B7%E5%8F%96topn%E6%8E%92%E8%A1%8C%E6%A6%9C?type=123&share=true&like=yihuihui";
        URI u = URI.create(uri);
        System.out.println(u);
    }

}
