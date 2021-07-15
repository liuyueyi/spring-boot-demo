package com.git.hui.spring.json.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author yihui
 * @date 2021/7/14
 */
public class JacksonUtil {

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        // 忽略 transient 关键字的配置
        // case1
//        objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
        // case2
        objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.ANY).withGetterVisibility(JsonAutoDetect.Visibility.NONE).withIsGetterVisibility(JsonAutoDetect.Visibility.NONE));

        // 反序列化时，找不到属性时，忽略字段
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

// key 为null，不抛异常，改用"null"
objectMapper.getSerializerProvider().setNullKeySerializer(new JsonSerializer<Object>() {
    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeFieldName("null");
    }
});

        // 忽略null字段
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static <T> String encode(T obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static <T> T decode(String str, Class<T> clz) {
        try {
            return objectMapper.readValue(str, clz);
        } catch (Exception e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static <T> T decode(String str, Type type) {
        try {
            return objectMapper.readValue(str, objectMapper.getTypeFactory().constructType(type));
        } catch (Exception e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static Map toMap(String str) {
        try {
            return objectMapper.readValue(str, Map.class);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static List toList(String str) {
        try {
            return objectMapper.readValue(str, List.class);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static JsonNode toObj(String str) {
        try {
            return objectMapper.readTree(str);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e);
        }
    }


    /**
     * 驼峰转下换线
     *
     * @param obj
     * @return
     */
    public static String toUnderStr(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        // 驼峰转下划线
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
        // json串只包含非null的字段
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static <T> T fromUnderStr(String str, Class<T> clz) {
        ObjectMapper objectMapper = new ObjectMapper();
        // 忽略 transient 修饰的属性
        objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
        // 驼峰转下划线
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        // 忽略找不到的字段
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            return objectMapper.readValue(str, clz);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e);
        }
    }
}
