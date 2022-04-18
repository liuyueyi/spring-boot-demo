package com.git.hui.json.test;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.Data;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 序列化时忽略Map中的某些字段
 *
 * @author yihui
 * @date 2021/10/29
 */
public class Demo {
    private static <T> Map<String, T> newMap(String key, T val, Object... kv) {
        Map<String, T> ans = new HashMap<>(8);
        ans.put(key, val);
        for (int i = 0, size = kv.length; i < size; i += 2) {
            ans.put(String.valueOf(kv[i]), (T) kv[i + 1]);
        }
        return ans;
    }

    @Test
    public void forEachParse() {
        Map<String, Integer> map = newMap("k", 1, "a", 2, "b", 3);
        Map<String, String> ans = new HashMap<>(map.size());
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            ans.put(entry.getKey(), String.valueOf(entry.getValue()));
        }
        System.out.println(ans);
    }

    @Test
    public void stream() {
        Map<String, Integer> map = newMap("k", 1, "a", 2, "b", 3);
        Map<String, String> ans = map.entrySet().stream().collect(
                Collectors.toMap(Map.Entry::getKey, s -> String.valueOf(s.getValue()), (a, b) -> a));
        System.out.println(ans);
    }

    @Test
    public void transfer() {
        Map<String, Integer> map = newMap("k", 1, "a", 2, "b", 3);
        Map<String, String> ans =  Maps.transformValues(map, String::valueOf);
        System.out.println(ans);
    }

    private static Gson getGson() {
        return new GsonBuilder().registerTypeAdapter(HashMap.class, new IgnoreMapTypeAdapter()).create();
    }

    public static class IgnoreMapTypeAdapter extends TypeAdapter<HashMap> {

        @Override
        public void write(JsonWriter out, HashMap value) throws IOException {
            Set<Map.Entry<Object, Object>> set = value.entrySet();
            out.beginObject();
            for (Map.Entry<Object, Object> entry : set) {
                String strKey = String.valueOf(entry.getKey());
                if (strKey.equalsIgnoreCase("pwd")) {
                    continue;
                }
                out.name(strKey);
                Object v = entry.getValue();
                if (v instanceof String) {
                    out.value((String) v);
                } else if (v instanceof Number) {
                    out.value((Number) v);
                } else if (v instanceof Boolean) {
                    out.value((Boolean) v);
                } else if (v.getClass() == HashMap.class) {
                    write(out, (HashMap) v);
                } else {
                    out.value(getGson().toJson(v));
                }
            }
            out.endObject();
        }

        @Override
        public HashMap read(JsonReader in) throws IOException {
            Gson gson = new Gson();
            return gson.fromJson(in, HashMap.class);
        }
    }

    @Test
    public void testCase() {
        Gson gson = getGson();

        Map map = newMap("user", "yihui", "pwd", 123455, "sub", newMap("key", "lll", "v", 1234L, "pwd", "abc"), "list", Arrays.asList(1, 2, 3));
        String str = gson.toJson(map);
        System.out.println(str);
        System.out.println(new Gson().toJson(map));
    }


    @Data
    public static class UserDemo {
        private String name;
        private Long id;
        private boolean adult;
    }

    @Test
    public void testStreamOut() throws Exception {
        JsonWriter writer = new JsonWriter(new OutputStreamWriter(System.out));
        writer.beginObject()
                .name("name").value("包青天")
                .name("age").value(24)
                .name("email").nullValue()
                .name("skill")
                .beginArray()
                .value("Java")
                .value("Python")
                .endArray()
                .endObject();
        writer.close();
    }


}
