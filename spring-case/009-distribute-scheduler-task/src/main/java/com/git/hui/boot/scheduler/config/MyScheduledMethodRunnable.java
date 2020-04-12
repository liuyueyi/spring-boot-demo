package com.git.hui.boot.scheduler.config;

import com.git.hui.boot.scheduler.ano.DistributeTask;
import com.git.hui.boot.scheduler.lock.ExecutiveLock;
import org.springframework.scheduling.support.ScheduledMethodRunnable;

import java.lang.reflect.Method;

/**
 * Created by @author yihui in 20:29 20/4/10.
 */
public class MyScheduledMethodRunnable extends ScheduledMethodRunnable {

    private final DistributeTask distributeTask;

    public MyScheduledMethodRunnable(Object target, Method method) {
        super(target, method);
        distributeTask = method.getAnnotation(DistributeTask.class);
    }

    @Override
    public void run() {
        if (distributeTask == null) {
            super.run();
        } else {
            String lock = getMethod().getName();
            if (ExecutiveLock.instance.tryLock(distributeTask.key(), lock)) {
                System.out.println("扩展 run: " + lock);
                super.run();
            } else {
                System.out.println("扩展 ignore: " + lock);
            }
        }
    }
}
