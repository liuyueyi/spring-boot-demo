package com.git.hui.spring.json.util;

import com.git.hui.spring.json.features.GsonNumberFixDeserializer;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author yihui
 * @date 2021/7/14
 */
public class GsonUtil {

    private static Gson gson = new Gson();

    public static <T> String encode(T obj) {
        // 输出value为null的值
        // new GsonBuilder().serializeNulls().create().toJson(obj);
        return gson.toJson(obj);
    }

    public static <T> T decode(String str, Class<T> clz) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(new TypeToken<Map>() {
        }.getType(), new GsonNumberFixDeserializer());
        Gson gson = gsonBuilder.create();
        return gson.fromJson(str, clz);
    }

    public static <T> T decode(String str, Type type) {
    return gson.fromJson(str, type);
}

    public static Map toMap(String str) {
        return decode(str, Map.class);
    }

    public static List toList(String str) {
        return gson.fromJson(str, List.class);
    }

    public static JsonObject toObj(String str) {
        return JsonParser.parseString(str).getAsJsonObject();
    }

    public static JsonArray toAry(String str) {
        return JsonParser.parseString(str).getAsJsonArray();
    }

    /**
     * 转下划线
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String toUnderStr(T obj) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        // 驼峰转下划线
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(obj);
    }

    public static <T> T fromUnderStr(String str, Class<T> clz) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        // 下划线的json串，反序列化为驼峰
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(str, clz);
    }
}
