package com.git.hui.boot.redis.demo;

import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by @author yihui in 20:47 20/10/10.
 */
@Service
public class GeoBean {
    private final StringRedisTemplate redisTemplate;

    public GeoBean(StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = stringRedisTemplate;
    }

    /**
     * 添加geo信息
     *
     * @param key       缓存key
     * @param longitude 经度
     * @param latitude  纬度
     * @param member    位置名
     */
    public void add(String key, double longitude, double latitude, String member) {
        redisTemplate.opsForGeo().add(key, new Point(longitude, latitude), member);
    }

    /**
     * 获取某个地方的坐标
     *
     * @param key
     * @param member
     * @return
     */
    public List<Point> get(String key, String... member) {
        List<Point> list = redisTemplate.opsForGeo().position(key, member);
        return list;
    }

    /**
     * 判断两个地点的距离
     *
     * @param key
     * @param source
     * @param dest
     * @return
     */
    public Distance distance(String key, String source, String dest) {
        return redisTemplate.opsForGeo().distance(key, source, dest);
    }

    public void near(String key, double longitude, double latitude) {
        //longitude,latitude
        Circle circle = new Circle(longitude, latitude, 5 * Metrics.KILOMETERS.getMultiplier());
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                .includeDistance()
                .includeCoordinates()
                .sortAscending().limit(5);
        GeoResults<RedisGeoCommands.GeoLocation<String>> results = redisTemplate.opsForGeo()
                .radius(key, circle, args);
        System.out.println(results);
    }

    public void nearByPlace(String key, String member) {
        Distance distance = new Distance(5, Metrics.KILOMETERS);
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                .includeDistance()
                .includeCoordinates()
                .sortAscending()
                .limit(5);
        GeoResults<RedisGeoCommands.GeoLocation<String>> results = redisTemplate.opsForGeo()
                .radius(key, member, distance, args);
        System.out.println(results);
    }

    public void geoHash(String key) {
        List<String> results = redisTemplate.opsForGeo()
                .hash(key, "北京", "上海", "深圳");
        System.out.println(results);
    }
}
