package com.git.hui.boot.zk;

import com.git.hui.boot.zk.lock.ZkLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * @author yihui
 * @date 2021/4/12
 */
@SpringBootApplication
public class Application {

    private void tryLock(long time) {
        ZkLock zkLock = null;
        try {
            zkLock = new ZkLock("/lock");
            System.out.println("尝试获取锁: " + Thread.currentThread() + " at: " + LocalDateTime.now());
            boolean ans = zkLock.lock();
            System.out.println("执行业务逻辑:" + Thread.currentThread() + " at:" + LocalDateTime.now());
            Thread.sleep(time);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (zkLock != null) {
                zkLock.unlock();
            }
        }
    }

    public Application() throws IOException, InterruptedException {
        new Thread(() -> tryLock(10_000)).start();

        Thread.sleep(1000);
        // 获取锁到执行锁会有10s的间隔，因为上面的线程抢占到锁，并持有了10s
        new Thread(() -> tryLock(1_000)).start();
        System.out.println("---------over------------");

        Scanner scanner = new Scanner(System.in);
        String ans = scanner.next();
        System.out.println("---> over --->" + ans);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}
