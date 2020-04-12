package com.git.hui.boot.scheduler.lock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by @author yihui in 20:33 20/4/10.
 */
public class ExecutiveLock {

    private Map<String, String> lockMap = new ConcurrentHashMap<>();

    public static final ExecutiveLock instance = new ExecutiveLock();

    private ExecutiveLock() {
    }

    public boolean tryLock(String key, String lockVal) {
        String old = lockMap.computeIfAbsent(key, (s) -> lockVal);
        return lockVal.equalsIgnoreCase(old);
    }
}
