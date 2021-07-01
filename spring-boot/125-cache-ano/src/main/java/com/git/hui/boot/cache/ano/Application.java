package com.git.hui.boot.cache.ano;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.git.hui.boot.cache.ano.manager.TtlRedisCacheManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yihui
 * @date 2021/2/22
 */
@EnableCaching
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }


    /**
     * 使用redis cache manager 实现集中式的缓存控制
     *
     * 注意，测试集中式缓存的时候，注释掉下面的 ttlCacheManager; 如果测试扩展的缓存失效时间，则注释这个，放开下面的ttlCacheManager
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return new RedisCacheManager(
                RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
                // 默认策略，未配置的 key 会使用这个
                this.getRedisCacheConfigurationWithTtl(60),
                // 指定 key 策略
                this.getRedisCacheConfigurationMap()
        );
    }

    @Primary
    @Bean
    public RedisCacheManager ttlCacheManager(RedisConnectionFactory redisConnectionFactory) {
        return new TtlRedisCacheManager(RedisCacheWriter.lockingRedisCacheWriter(redisConnectionFactory),
                // 默认缓存配置
                this.getRedisCacheConfigurationWithTtl(60));
    }

    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>(8);
        // 自定义设置缓存时间
        redisCacheConfigurationMap.put("k0", this.getRedisCacheConfigurationWithTtl(60 * 60));
        return redisCacheConfigurationMap;
    }

    private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer seconds) {
        // 设置 json 序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer)).
                // 设置过期时间
                        entryTtl(Duration.ofSeconds(seconds));

        return redisCacheConfiguration;
    }

}
