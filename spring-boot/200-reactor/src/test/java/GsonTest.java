import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import org.assertj.core.util.Maps;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by @author yihui in 22:01 20/8/24.
 */
public class GsonTest {


    @Test
    public void testGson() {
        Gson gson = new Gson();

        Map map = new HashMap<>();
        map.put("a", "hello world");
        map.put(12, true);
        map.put("array", Arrays.asList("a", "c", "f", 12));

        String ans = gson.toJson(map);
        System.out.println(ans);
    }

    public static class BaseBean {
        private int age;

        private String name;

        private transient int code;

        private String email;

        public BaseBean() {
        }

        @Override
        public String toString() {
            return "BaseBean{" + "age=" + age + ", name='" + name + '\'' + ", code=" + code + ", email='" + email +
                    '\'' + '}';
        }
    }

    @Test
    public void testObjGson() {
        BaseBean bean = new BaseBean();
        bean.age = 10;
        bean.code = 20;
        bean.name = "一灰灰blog";

        Gson gson = new Gson();
        String str = gson.toJson(bean);
        System.out.println("json str: " + str);

        BaseBean out = gson.fromJson(str, BaseBean.class);
        System.out.println("after deserialization: " + out);

        Gson gsonWithNUll = new GsonBuilder().serializeNulls().create();
        System.out.println("serialize with null: " + gsonWithNUll.toJson(bean));


        Map map = gson.fromJson(str, Map.class);
        System.out.println(map);
    }


    @Test
    public void testParser() {
        Map map = new HashMap();
        map.put("a", "hello world");
        map.put(12, true);
        map.put("array", Arrays.asList("a", "c", "f", 12));
        map.put("obj", Maps.newHashMap("k", "v"));

        Gson gson = new Gson();
        String str = gson.toJson(map);


        JsonObject obj = JsonParser.parseString(str).getAsJsonObject();
        String a = obj.get("a").getAsString();
        boolean b = obj.get("12").getAsBoolean();
        JsonArray ary = obj.get("array").getAsJsonArray();
        JsonObject o = obj.get("obj").getAsJsonObject();

        System.out.println("a:" + a + " b:" + b + " ary:" + ary + " o:" + o);
    }

    @Data
    public static class ResWrapper<T> {
        private T data;
        private int code;
        private String msg;
    }

    @Data
    public static class User {
        private int age;
        private String name;

        public User(int age, String name) {
            this.age = age;
            this.name = name;
        }
    }

    @Test
    public void testGenri() {
        ResWrapper<User> wrapper = new ResWrapper<>();
        wrapper.code = 0;
        wrapper.msg = "name";
        wrapper.data = new User(18, "一灰灰");

        Gson gson = new Gson();
        String str = gson.toJson(wrapper);

        Type type = new TypeToken<ResWrapper<User>>() {}.getType();
        ResWrapper<User> out = gson.fromJson(str, type);
        System.out.println(out);

        TypeReference<ResWrapper<User>> typeReference = new TypeReference<ResWrapper<User>>(){};
        out = JSONObject.parseObject(str, typeReference);
    }
}
