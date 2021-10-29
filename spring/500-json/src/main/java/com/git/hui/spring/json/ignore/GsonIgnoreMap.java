package com.git.hui.spring.json.ignore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.testng.annotations.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 序列化时忽略Map中的某些字段
 *
 * @author yihui
 * @date 2021/10/29
 */
public class GsonIgnoreMap {
private static Map<String, Object> newMap(String key, Object val, Object... kv) {
    Map<String, Object> ans = new HashMap<>(8);
    ans.put(key, val);
    for (int i = 0, size = kv.length; i < size; i += 2) {
        ans.put(String.valueOf(kv[i]), kv[i + 1]);
    }
    return ans;
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
            } else if (v.getClass() == HashMap.class){
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

}
