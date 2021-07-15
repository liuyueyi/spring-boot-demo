package com.git.hui.spring.json.features;

import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 整数转int/long, 而不是所有的都转double
 *
 * @author yihui
 * @date 2021/7/15
 */
public class GsonNumberFixDeserializer implements JsonDeserializer<Map> {
    @Override
    public Map deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return (Map) read(jsonElement);
    }

    public Object read(JsonElement in) {
        if (in.isJsonArray()) {
            List<Object> list = new ArrayList<>();
            JsonArray arr = in.getAsJsonArray();
            for (JsonElement anArr : arr) {
                list.add(read(anArr));
            }
            return list;
        } else if (in.isJsonObject()) {
            Map<String, Object> map = new LinkedTreeMap<>();
            JsonObject obj = in.getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entitySet = obj.entrySet();
            for (Map.Entry<String, JsonElement> entry : entitySet) {
                map.put(entry.getKey(), read(entry.getValue()));
            }
            return map;
        } else if (in.isJsonPrimitive()) {
            JsonPrimitive prim = in.getAsJsonPrimitive();
            if (prim.isBoolean()) {
                return prim.getAsBoolean();
            } else if (prim.isString()) {
                return prim.getAsString();
            } else if (prim.isNumber()) {
                Number num = prim.getAsNumber();
                if (Math.ceil(num.doubleValue()) != num.longValue()) {
                    return num.doubleValue();
                }
                if (num.doubleValue() > Integer.MAX_VALUE || num.doubleValue() < Integer.MIN_VALUE) {
                    return num.longValue();
                }
                return num.longValue();
            }
        }
        return null;
    }
}
