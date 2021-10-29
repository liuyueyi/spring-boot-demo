package com.git.hui.spring.json.ignore;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.internal.ConstructorConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.testng.annotations.Test;

import java.lang.annotation.*;
import java.util.*;

/**
 * Created by @author yihui in 22:01 20/8/24.
 */
public class GsonTest {

    @Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface IgnoreField {
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GItem {
        @Expose
        private String user;
        // @IgnoreField
        private String pwd;
    }

    @Test
    public void testPrint() {
        GItem item = new GItem("一灰灰", "yihui");
        String ans = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(item);
        System.out.println(ans);
    }

    @Test
    public void testExclude() {
        Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                if (fieldAttributes.getAnnotation(IgnoreField.class) != null) {
                    // 包含这个注解的，直接忽略
                    return true;
                }

                // 成员白名单
                if (fieldAttributes.getName().equalsIgnoreCase("pwd")) {
                    return true;
                }
                return false;
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                if (aClass.isAnnotationPresent(IgnoreField.class)) {
                    return true;
                }
                return false;
            }
        }).registerTypeAdapterFactory(new MyMapTypeAdapterFactory(new ConstructorConstructor(new HashMap<>()), false)).create();

        Map map = new HashMap();
        map.put("user", "yihuihui");
        map.put("pwd", "123456789");
        System.out.println(gson.toJson(map));

        GItem item = new GItem();
        item.setUser("一灰灰");
        item.setPwd("123456");

        System.out.println(gson.toJson(item));

        map.put("item", item);
        System.out.println(gson.toJson(map));

        List list = new ArrayList<>();
        list.add(map);
        System.out.println(gson.toJson(list));


        map.put("map", newMap("key", "value", "pwd", "password"));
        System.out.println(gson.toJson(map));
    }

    private static Map<String, Object> newMap(String key, Object val, Object... kv) {
        Map<String, Object> ans = new Hashtable<>();
        ans.put(key, val);
        for (int i = 0, size = kv.length; i < size; i += 2) {
            ans.put(String.valueOf(kv[i]), kv[i + 1]);
        }
        return ans;
    }
}
