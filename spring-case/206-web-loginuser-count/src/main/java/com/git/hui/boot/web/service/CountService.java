package com.git.hui.boot.web.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 计数服务
 *
 * @author YiHui
 * @date 2023/3/25
 */
@Service
public class CountService {

    private AtomicInteger cnt = new AtomicInteger(0);

    public void incr(int cnt) {
        this.cnt.addAndGet(cnt);
    }

    public int getOnlineCnt() {
        return cnt.get();
    }

}
