package com.git.hui.spring.json.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author yihui
 * @date 2021/7/14
 */
public class FastjsonUtil {

    /**
     * 转json string
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String encode(T obj) {
        // 输出value为null的
        // return JSONObject.toJSONString(obj, SerializerFeature.WriteMapNullValue);
        // 若key不是String类型，转String
        return JSONObject.toJSONString(obj, SerializerFeature.WriteNonStringKeyAsString);
    }


    /**
     * 反序列化
     *
     * @param str
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T decode(String str, Class<T> clz) {
        return JSONObject.parseObject(str, clz);
    }

    /**
     * 反序列化，主要用于泛型的bean对象处理
     *
     * @param str
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T decode(String str, Type type) {
        return JSONObject.parseObject(str, type);
    }

    public static Map toMap(String str) {
        // 禁用浮点数转BigDecimal
        int features = JSON.DEFAULT_PARSER_FEATURE & ~Feature.UseBigDecimal.getMask();
        // 对象转Map，而不是JSONObject
        features |= Feature.CustomMapDeserializer.getMask();
        features |= Feature.SortFeidFastMatch.getMask();
        return JSONObject.parseObject(str, Map.class, features);
    }

    public static List toList(String str) {
        return JSONObject.parseObject(str, List.class);
    }

    public static JSONObject toObj(String str) {
        return JSONObject.parseObject(str);
    }

    public static JSONArray toAry(String str) {
        return JSONArray.parseArray(str);
    }

public static <T> String toUnderStr(T obj) {
    // 驼峰转下划线
    SerializeConfig serializeConfig = new SerializeConfig();
    // CamelCase 常见的驼峰格式
    // PascalCase 单次首字母大写驼峰
    // SnakeCase 下划线
    // KebabCase 中划线
    serializeConfig.setPropertyNamingStrategy(PropertyNamingStrategy.SnakeCase);
    return JSONObject.toJSONString(obj, serializeConfig, SerializerFeature.PrettyFormat, SerializerFeature.IgnoreNonFieldGetter);
}
}
