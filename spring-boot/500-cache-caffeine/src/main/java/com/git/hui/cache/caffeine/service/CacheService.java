package com.git.hui.cache.caffeine.service;

import com.github.benmanes.caffeine.cache.*;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author YiHui
 * @date 2023/3/5
 */
@Service
public class CacheService {
    // 手动加载缓存
    private Cache<String, Integer> uidCache;

    // 在创建时，自动指定加载规则
    private LoadingCache<String, Integer> autoCache;


    // 手动异步加载缓存
    private AsyncCache<String, Integer> asyncUidCache;

    // 自动异步加载缓存
    private AsyncLoadingCache<String, Integer> asyncAutoCache;

    private AtomicInteger idGen;

    public CacheService() {
        // 手动缓存加载方式
        idGen = new AtomicInteger(100);
        uidCache = Caffeine.newBuilder()
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .maximumSize(100)
                .build();

        autoCache = Caffeine.newBuilder()
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .maximumSize(100)
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public @Nullable Integer load(@NonNull String key) throws Exception {
                        return idGen.getAndAdd(1);
                    }
                });


        asyncUidCache = Caffeine.newBuilder()
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .maximumSize(100)
                .buildAsync();

        asyncAutoCache = Caffeine.newBuilder()
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .maximumSize(100)
                .buildAsync(new CacheLoader<String, Integer>() {
                    @Override
                    public @Nullable Integer load(@NonNull String key) throws Exception {
                        return idGen.getAndAdd(1);
                    }
                });
    }

    public void getUid(String session) {
        // 重新再取一次，这次应该就不是重新初始化了
        Integer uid = uidCache.getIfPresent(session);
        System.out.println("查看缓存! 当没有的时候返回的是 uid: " + uid);

        // 第二个参数表示当不存在时，初始化一个，并写入缓存中
        uid = uidCache.get(session, (key) -> 10);
        System.out.println("初始化一个之后，返回的是: " + uid);

        // 移除缓存
        uidCache.invalidate(session);

        // 手动添加一个缓存
        uidCache.put(session + "_2", 11);

        // 查看所有的额缓存
        Map map = uidCache.asMap();
        System.out.println("total: " + map);

        // 干掉所有的缓存
        uidCache.invalidateAll();
    }


    public void autoGetUid(String session) {
        Integer uid = autoCache.getIfPresent(session);
        System.out.println("自动加载，没有时返回: " + uid);

        uid = autoCache.get(session);
        System.out.println("自动加载，没有时自动加载一个: " + uid);

        // 批量查询
        List<String> keys = Arrays.asList(session, session + "_1");
        Map<String, Integer> map = autoCache.getAll(keys);
        System.out.println("批量获取，一个存在一个不存在时：" + map);

        // 手动加一个
        autoCache.put(session + "_2", 11);
        Map total = autoCache.asMap();
        System.out.println("total: " + total);
    }

    public void asyncGetUid(String session) throws ExecutionException, InterruptedException {
        // 重新再取一次，这次应该就不是重新初始化了
        CompletableFuture<Integer> uid = asyncUidCache.getIfPresent(session);
        System.out.println("查看缓存! 当没有的时候返回的是 uid: " + (uid == null ? "null" : uid.get()));

        // 第二个参数表示当不存在时，初始化一个，并写入缓存中
        uid = asyncUidCache.get(session, (key) -> 10);
        System.out.println("初始化一个之后，返回的是: " + uid.get());

        // 手动塞入一个缓存
        asyncUidCache.put(session + "_2", CompletableFuture.supplyAsync(() -> 12));

        // 移除缓存
        asyncUidCache.synchronous().invalidate(session);
        // 查看所有的额缓存
        System.out.println("print total cache:");
        for (Map.Entry<String, CompletableFuture<Integer>> sub : asyncUidCache.asMap().entrySet()) {
            System.out.println(sub.getKey() + "==>" + sub.getValue().get());
        }
        System.out.println("total over");
    }

    public void asyncAutoGetUid(String session) {
        try {
            CompletableFuture<Integer> uid = asyncAutoCache.getIfPresent(session);
            System.out.println("自动加载，没有时返回: " + (uid == null ? "null" : uid.get()));

            uid = asyncAutoCache.get(session);
            System.out.println("自动加载，没有时自动加载一个: " + uid.get());

            // 批量查询
            List<String> keys = Arrays.asList(session, session + "_1");
            CompletableFuture<Map<String, Integer>> map = asyncAutoCache.getAll(keys);
            System.out.println("批量获取，一个存在一个不存在时：" + map.get());

            // 手动加一个
            asyncAutoCache.put(session + "_2", CompletableFuture.supplyAsync(() -> 11));

            // 查看所有的额缓存
            System.out.println("print total cache:");
            for (Map.Entry<String, CompletableFuture<Integer>> sub : asyncAutoCache.asMap().entrySet()) {
                System.out.println(sub.getKey() + "==>" + sub.getValue().get());
            }
            System.out.println("total over");

            // 清空所有缓存
            asyncAutoCache.synchronous().invalidateAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void test() {
        String key = UUID.randomUUID().toString();

        System.out.println("====== 手动加载 ====");
        getUid(key);
        System.out.println("---------------------");

        System.out.println("======= 自动加载 =====");
        autoGetUid(key);
        System.out.println("---------------------");

        System.out.println("======= 手动异步加载 =====");
        try {
            asyncGetUid(key);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("---------------------");


        System.out.println("======= 自动异步加载 =====");
        asyncAutoGetUid(key);
        System.out.println("---------------------");
    }
}
