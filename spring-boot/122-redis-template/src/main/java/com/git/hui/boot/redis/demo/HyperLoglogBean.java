package com.git.hui.boot.redis.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 非精确的统计，比如日活
 * <p>
 * 划分：16384个桶
 * 每个桶 6个字节
 * <p>
 * Redis 的 HyperLogLog 通过牺牲准确率来减少内存空间的消耗，只需要12K内存，在标准误差0.81%的前提下，能够统计2^64个数据。所以 HyperLogLog 是否适合在比如统计日活月活此类的对精度要不不高的场景。
 * <p>
 * 2^64 数字，转二进制，低 14位，作为定位桶的序列； 剩下50位，从左到右，找到第一个出现1的位置，设置到对应的桶里
 * <p>
 * - 如第一个出现1的位置为8，首先判断桶里面是否有数据，有且数据小于8，则更新桶中的数据为8
 * - 一个演示 hyperloglog 动画的网站: http://content.research.neustar.biz/blog/hll.html
 * <p>
 * Created by @author yihui in 14:06 20/10/13.
 */
public class HyperLoglogBean {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void test() {
        // hyperloglog 单纯的使用姿势比较简单，精妙的地方在于这个算法的设计思路，以及应用场景，量大的场景下，节省内存神器
        String key = "hll_test";
        add(key, "hello");
        System.out.println(count(key));
        add(key, "world");
        System.out.println(count(key));

        String key2 = "hll_test_2";
        add(key2, "welcome");
        String key3 = "hll_out";
        merge(key3, key, key2);
        System.out.println(count(key3));
    }

    public boolean add(String key, String obj) {
        // pfadd key obj
        return stringRedisTemplate.opsForHyperLogLog().add(key, obj) > 0;
    }

    public long count(String key) {
        // pfcount 非精准统计 key的计数
        return stringRedisTemplate.opsForHyperLogLog().size(key);
    }

    public boolean merge(String out, String... key) {
        // pfmerge out key1 key2  ---> 将key1 key2 合并成一个新的hyperloglog out
        return stringRedisTemplate.opsForHyperLogLog().union(out, key) > 0;
    }
}
