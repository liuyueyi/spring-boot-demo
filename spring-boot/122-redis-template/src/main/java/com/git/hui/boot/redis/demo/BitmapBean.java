package com.git.hui.boot.redis.demo;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * bitmap 位图，用来统计计数比较合适
 * Created by @author yihui in 17:27 20/10/10.
 */
@Service
public class BitmapBean {

    private final StringRedisTemplate redisTemplate;

    public BitmapBean(StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = stringRedisTemplate;
    }


    /**
     * 设置标记位
     *
     * @param key
     * @param offset
     * @param tag
     * @return
     */
    public Boolean mark(String key, long offset, boolean tag) {
        return redisTemplate.opsForValue().setBit(key, offset, tag);
    }

    public Boolean mark2(String key, long offset, boolean tag) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.setBit(key.getBytes(), offset, tag);
            }
        });
    }

    /**
     * 判断是否标记过
     *
     * @param key
     * @param offest
     * @return
     */
    public Boolean container(String key, long offest) {
        return redisTemplate.opsForValue().getBit(key, offest);
    }

    /**
     * 统计计数
     *
     * @param key
     * @return
     */
    public long bitCount(String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.bitCount(key.getBytes());
            }
        });
    }
}
