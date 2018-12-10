package com.git.hui.boot.redis.demo;

import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by @author yihui in 18:05 18/11/6.
 */
@Component
public class KVBean {

    private final StringRedisTemplate redisTemplate;

    public KVBean(StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = stringRedisTemplate;
    }

    // kv数据结构的测试相关

    /**
     * 设置并获取之间的结果，要求key，value都不能为空；如果之前没有值，返回null
     *
     * @param key
     * @param value
     * @return
     */
    public byte[] setAndGetOldValue(String key, String value) {
        return redisTemplate.execute((RedisCallback<byte[]>) con -> con.getSet(key.getBytes(), value.getBytes()));
    }

    public Boolean setValue(String key, String value) {
        return redisTemplate
                .execute((RedisCallback<Boolean>) connection -> connection.set(key.getBytes(), value.getBytes()));
    }

    public byte[] getValue(String key) {
        return redisTemplate.execute((RedisCallback<byte[]>) connection -> connection.get(key.getBytes()));
    }

    public Boolean mSetValue(Map<String, String> values) {
        Map<byte[], byte[]> map = new HashMap<>(values.size());
        for (Map.Entry<String, String> entry : values.entrySet()) {
            map.put(entry.getKey().getBytes(), entry.getValue().getBytes());
        }

        return redisTemplate.execute((RedisCallback<Boolean>) con -> con.mSet(map));
    }

    public List<byte[]> mGetValue(List<String> keys) {
        return redisTemplate.execute((RedisCallback<List<byte[]>>) con -> {
            byte[][] bkeys = new byte[keys.size()][];
            for (int i = 0; i < keys.size(); i++) {
                bkeys[i] = keys.get(i).getBytes();
            }
            return con.mGet(bkeys);
        });
    }


    // 自增、自减方式实现计数

    /**
     * 实现计数的加/减（ value为负数表示减）
     *
     * @param key
     * @param value
     * @return 返回redis中的值
     */
    public Long incr(String key, long value) {
        return redisTemplate.execute((RedisCallback<Long>) con -> con.incrBy(key.getBytes(), value));
    }

    public Long decr(String key, long value) {
        return redisTemplate.execute((RedisCallback<Long>) con -> con.decrBy(key.getBytes(), value));
    }


    // bitmap的测试相关

    public Boolean setBit(String key, Integer index, Boolean tag) {
        return redisTemplate.execute((RedisCallback<Boolean>) con -> con.setBit(key.getBytes(), index, tag));
    }

    public Boolean getBit(String key, Integer index) {
        return redisTemplate.execute((RedisCallback<Boolean>) con -> con.getBit(key.getBytes(), index));
    }

    /**
     * 统计bitmap中，value为1的个数，非常适用于统计网站的每日活跃用户数等类似的场景
     *
     * @param key
     * @return
     */
    public Long bitCount(String key) {
        return redisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes()));
    }

    public Long bitCount(String key, int start, int end) {
        return redisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes(), start, end));
    }

    public Long bitOp(RedisStringCommands.BitOperation op, String saveKey, String... desKey) {
        byte[][] bytes = new byte[desKey.length][];
        for (int i = 0; i < desKey.length; i++) {
            bytes[i] = desKey[i].getBytes();
        }
        return redisTemplate.execute((RedisCallback<Long>) con -> con.bitOp(op, saveKey.getBytes(), bytes));
    }


    public void bitMapTest() {
        byte[] bytes = getValue("bitKey");
        BitSet set = BitSet.valueOf(bytes);
        System.out.println(set);

        setValue("newBitKey", new String(set.toByteArray()));

        BitSet newSet = fromByteArrayReverse(bytes);
        System.out.println(newSet);
    }

    public static BitSet fromByteArrayReverse(final byte[] bytes) {
        final BitSet bits = new BitSet();
        if (bytes != null) {
            for (int i = 0; i < bytes.length * 8; i++) {
                if ((bytes[i / 8] & (1 << (7 - (i % 8)))) != 0) {
                    bits.set(i);
                }
            }
        }
        return bits;
    }
}
