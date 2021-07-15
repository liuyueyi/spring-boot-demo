package com.git.hui.spring.json;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.git.hui.spring.json.bean.GenericBean;
import com.git.hui.spring.json.bean.SelfRefBean;
import com.git.hui.spring.json.bean.SelfRefBean2;
import com.git.hui.spring.json.bean.SimpleBean;
import com.git.hui.spring.json.util.FastjsonUtil;
import com.git.hui.spring.json.util.GsonUtil;
import com.git.hui.spring.json.util.JacksonUtil;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yihui
 * @date 2021/7/14
 */
public class Application {

    private static void fastJsonTest(SimpleBean simpleBean) {
        String str = FastjsonUtil.encode(simpleBean);
        SimpleBean out = FastjsonUtil.decode(str, SimpleBean.class);
        System.out.println(out);

        GenericBean gbean1 = FastjsonUtil.decode(str, GenericBean.class);
        System.out.println(gbean1);

        GenericBean<Map> gbean2 = FastjsonUtil.decode(str, new com.alibaba.fastjson.TypeReference<GenericBean<Map>>() {
        }.getType());
        System.out.println(gbean2);

        Map map = FastjsonUtil.toMap(str);
        System.out.println(map);

        JSONObject obj = FastjsonUtil.toObj(str);
        System.out.println(obj);
        System.out.println("----------------------------");
    }

    private static void gsonTest(SimpleBean simpleBean) {
        String str = GsonUtil.encode(simpleBean);
        SimpleBean out = GsonUtil.decode(str, SimpleBean.class);
        System.out.println(out);

        GenericBean gbean1 = GsonUtil.decode(str, GenericBean.class);
        System.out.println(gbean1);

        GenericBean<Map> gbean2 = GsonUtil.decode(str, new com.google.gson.reflect.TypeToken<GenericBean<Map>>() {
        }.getType());
        System.out.println(gbean2);

        Map map = GsonUtil.toMap(str);
        System.out.println(map);

        JsonObject obj = GsonUtil.toObj(str);
        System.out.println(obj);
        System.out.println("----------------------------");
    }

    private static void jacksonTest(SimpleBean simpleBean) {
        String str = JacksonUtil.encode(simpleBean);
        SimpleBean out = JacksonUtil.decode(str, SimpleBean.class);
        System.out.println(out);

        GenericBean gbean1 = JacksonUtil.decode(str, GenericBean.class);
        System.out.println(gbean1);

        GenericBean<Map> gbean2 = JacksonUtil.decode(str, new com.fasterxml.jackson.core.type.TypeReference<GenericBean<Map>>() {
        }.getType());
        System.out.println(gbean2);

        Map map = JacksonUtil.toMap(str);
        System.out.println(map);

        JsonNode obj = JacksonUtil.toObj(str);
        System.out.println(obj);
        System.out.println("----------------------------");
    }

    /**
     * 测试json转Map时，默认值的处理场景
     */
    private static void defaultFields() {
        Map obj = new HashMap();
        obj.put("a", "a");
        obj.put("b", "b");

        Map map = new HashMap();
        map.put(1, 10);
        map.put("float", 12.3);
        map.put("date", new Date());
        map.put("char", 'a');
        map.put("boolean", true);
        map.put("long", 125555555541234L);
        map.put("list", Arrays.asList(1, 2, 3, "a", "b", "c"));
        map.put("map", obj);
        map.put(null, "null fields");

        String str = GsonUtil.encode(map);
        Map fm = FastjsonUtil.toMap(str);
        Map gm = GsonUtil.toMap(str);
        Map jm = JacksonUtil.toMap(str);
        System.out.println("-------");
    }

    private static void notMatch() {
        String str = "{\"extra\":{\"a\":\"123\",\"b\":345,\"c\":[\"1\",\"2\",\"3\"],\"d\":35.1},\"hello\":\"你好\",\"userId2\":12,\"userMoney\":12.3,\"userName\":\"yh\",\"userSkills\":[\"1\",\"2\",\"3\"]}";
        SimpleBean fBean = FastjsonUtil.decode(str, SimpleBean.class);
        SimpleBean gBean = GsonUtil.decode(str, SimpleBean.class);
        SimpleBean jBean = JacksonUtil.decode(str, SimpleBean.class);
        System.out.println("------------------");
    }

    private static void selfRef() {
        SelfRefBean bean = new SelfRefBean();
        bean.setName("yh");
        SelfRefBean2 bean2 = new SelfRefBean2();
        bean2.setName("yhh");
        bean2.setBean(bean);
        bean.setBean(bean2);

        String fStr = FastjsonUtil.encode(bean);
        String gStr = GsonUtil.encode(bean);
        String jStr = JacksonUtil.encode(bean);
        System.out.println(fStr + "\n" + gStr + "\n" + jStr);
    }


    /**
     * 驼峰与下划线的转换支持
     *
     * @param simpleBean
     */
    private static void toUnderLine(SimpleBean simpleBean) {
        String fstr = FastjsonUtil.toUnderStr(simpleBean);
        String gstr = GsonUtil.toUnderStr(simpleBean);
        String jstr = JacksonUtil.toUnderStr(simpleBean);

        System.out.println("-->FastJson:\n" + fstr + "\n-->Gson:\n" + gstr + "\n-->Jackson:\n" + jstr);

        // 下换线转驼峰，反序列化,FastJson默认支持; 如果是驼峰的json串，调用下面的FastJson可以正常工作；Gson与Jackson不行
        SimpleBean fbean = FastjsonUtil.decode(fstr, SimpleBean.class);
        SimpleBean gbean = GsonUtil.fromUnderStr(gstr, SimpleBean.class);
        SimpleBean jbean = JacksonUtil.fromUnderStr(jstr, SimpleBean.class);
        System.out.println("------");
    }


    public static void main(String[] args) {
        SimpleBean simpleBean = new SimpleBean().setUserId(12).setUserName("yh").setUserMoney(12.3D).setUserSkills(Arrays.asList("1", "2", "3"))
                .setExtra(new HashMap<String, Object>(8) {
                    private static final long serialVersionUID = 893460816623439571L;

                    {
                        put("a", "123");
                        put("b", 345);
                        put("c", Arrays.asList("1", "2", "3"));
                        put("d", 35.1f);
                    }
                });
//        fastJsonTest(simpleBean);
//        gsonTest(simpleBean);
//        jacksonTest(simpleBean);
//
//        toUnderLine(simpleBean);
//        defaultFields();

        // 属性为匹配的场景
        notMatch();

//        selfRef();
    }

}
