package com.git.hui.boot.redis.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * Created by @author yihui in 09:15 18/9/30.
 */
public class MultRedisClientChooser {
    public interface DataParser<T> {
        byte[] enc(Object obj);

        byte[] encList(List obj);

        T dec(byte[] bytes);

        List<T> decList(byte[] bytes);
    }

    public static class JsonDataParser<T> implements DataParser {
        private T type;

        public JsonDataParser(T type) {
            this.type = type;
        }

        @Override
        public byte[] enc(Object obj) {
            return JSONObject.toJSONString(obj).getBytes();
        }

        @Override
        public byte[] encList(List obj) {
            return JSONObject.toJSONString(obj).getBytes();
        }

        @Override
        public T dec(byte[] bytes) {
            if (type instanceof Class) {
                return JSONObject.parseObject(new String(bytes), (Class<T>) type);
            } else if (type instanceof TypeReference) {
                return JSONObject.parseObject(new String(bytes), ((TypeReference) type).getType());
            } else {
                throw new IllegalStateException("非法的反序列化类型");
            }
        }

        @Override
        public List<T> decList(byte[] list) {
            if (type instanceof Class) {
                return JSONObject.parseArray(new String(list), (Class<T>) type);
            } else {
                throw new IllegalStateException("非法的反序列化类型");
            }
        }
    }

    public static class ProtostuffParser<T> implements DataParser {

        private Class<T> type;

        public ProtostuffParser(Class<T> type) {
            this.type = type;
        }

        @Override
        public byte[] enc(Object obj) {
            return ProtoStuffUtil.serialize(obj);
        }

        @Override
        public byte[] encList(List obj) {
            return ProtoStuffUtil.serializeList(obj);
        }

        @Override
        public T dec(byte[] bytes) {
            return ProtoStuffUtil.deserialize(bytes, type);
        }

        @Override
        public List<T> decList(byte[] bytes) {
            return ProtoStuffUtil.deserializeList(bytes, type);
        }
    }

    public static class RedisClientSelector<T> {
        public static final RedisClientSelector DEFAULT_JSON_PARSER =
                new RedisClientSelector<>(new JsonDataParser<>(null));

        private DataParser<T> parser;
        private RedisTemplate<String, String> redisTemplate;

        public RedisClientSelector(DataParser<T> parser) {
            this.parser = parser;
            choose("default");
        }

        public RedisClientSelector<T> choose(String key) {
            this.redisTemplate = getTemplate(key);
            return this;
        }

        /**
         * get cache
         *
         * @param field
         * @return
         */
        public T get(final String field) {
            byte[] result =
                    redisTemplate.execute((RedisCallback<byte[]>) connection -> connection.get(field.getBytes()));
            if (result == null || result.length == 0) {
                return null;
            }

            return parser.dec(result);
        }

        public List<T> getList(final String field) {
            byte[] result =
                    redisTemplate.execute((RedisCallback<byte[]>) connection -> connection.get(field.getBytes()));
            if (result == null || result.length == 0) {
                return null;
            }

            return parser.decList(result);
        }

        /**
         * put cache
         *
         * @param field
         * @param obj
         * @param <T>
         * @return
         */
        public <T> void set(String field, T obj) {
            final byte[] value = parser.enc(obj);
            redisTemplate.execute((RedisCallback<Void>) connection -> {
                connection.set(field.getBytes(), value);
                return null;
            });
        }

        /**
         * put list cache
         *
         * @param field
         * @param objList
         * @param <T>
         * @return
         */
        public <T> void setList(String field, List<T> objList) {
            final byte[] value = parser.encList(objList);
            redisTemplate.execute((RedisCallback<Void>) connection -> {
                connection.set(field.getBytes(), value);
                return null;
            });
        }
    }

    static RedisTemplate<String, String> redisTemplate;

    public static void init(RedisTemplate<String, String> redisTemplate) {
        MultRedisClientChooser.redisTemplate = redisTemplate;
    }

    public static RedisTemplate<String, String> getTemplate(String key) {
        return redisTemplate;
    }

    public static <T> RedisClientSelector<T> jsonParser() {
        return RedisClientSelector.DEFAULT_JSON_PARSER;
    }

    public static <T> RedisClientSelector<T> jsonParser(TypeReference<T> type) {
        return new RedisClientSelector<T>(new JsonDataParser<>(type));
    }

    public static <T> RedisClientSelector<T> jsonParser(Class<T> clz) {
        return new RedisClientSelector<T>(new JsonDataParser<>(clz));
    }

    public static <T> RedisClientSelector<T> protostuffParser(Class<T> clz) {
        return new RedisClientSelector<T>(new ProtostuffParser<>(clz));
    }


}
