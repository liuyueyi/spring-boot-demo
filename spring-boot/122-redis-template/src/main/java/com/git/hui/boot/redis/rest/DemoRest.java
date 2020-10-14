package com.git.hui.boot.redis.rest;

import com.alibaba.fastjson.JSONObject;
import com.git.hui.boot.redis.demo.KVBean;
import com.git.hui.boot.redis.demo.ListBean;
import com.git.hui.boot.redis.demo.PubSubBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by @author yihui in 21:04 18/11/7.
 */
@RestController
@RequestMapping(path = "rest")
public class DemoRest {

    @Autowired
    private KVBean kvBean;

    @GetMapping(path = "show")
    public String showKv(String key) {
        Map<String, String> result = new HashMap<>(16);

        // kv test
        String kvKey = "kvKey";
        String kvVal = UUID.randomUUID().toString();
        kvBean.setValue(kvKey, kvVal);
        String kvRes = new String(kvBean.getValue(kvKey));
        result.put("kv get set", kvRes + "==>" + kvVal.equals(kvRes));


        // kv getSet

        // 如果原始数据存在时
        String kvOldRes = new String(kvBean.setAndGetOldValue(kvKey, kvVal + "==>new"));
        result.put("kv setAndGet", kvOldRes + " # " + new String(kvBean.getValue(kvKey)));

        // 如果原始数据不存在时
        byte[] kvOldResNull = kvBean.setAndGetOldValue("not exists", "...");
        result.put("kv setAndGet not exists", kvOldResNull == null ? "null" : new String(kvOldResNull));


        // 自增
        String cntKey = "kvIncrKey";
        long val = 10L;
        long incrRet = kvBean.incr(cntKey, val);
        String incrRes = new String(kvBean.getValue(cntKey));
        result.put("kv incr", incrRet + "#" + incrRes);


        // bitmap 测试
        String bitMapKey = "bitmapKey";
        kvBean.setBit(bitMapKey, 100, true);
        boolean bitRes = kvBean.getBit(bitMapKey, 100);
        boolean bitRes2 = kvBean.getBit(bitMapKey, 101);
        result.put("bitMap", bitRes + ">> true | " + bitRes2 + ">> false");
        return JSONObject.toJSONString(result);
    }


    @Autowired
    private ListBean listBean;

    @GetMapping(path = "list")
    public String showList() {
        Map<String, String> result = new HashMap<>(16);
        String key = "listKey";
        // 删除之前的缓存，避免影响测试数据
        listBean.delete(key);

        // 新增一个数据
        listBean.lpush(key, "hello");

        // 获取列表中的所有值
        List<String> redisVal = listBean.range(key, 0, -1);
        result.put("list", redisVal.toString());


        String indexVal = listBean.index(key, 0);
        result.put("index", indexVal + " == hello");


        listBean.lpush(key, "12");
        listBean.lpush(key, "23");
        listBean.lpush(key, "45");
        listBean.lpush(key, "67");
        listBean.trim(key, 0, 3);
        redisVal = listBean.range(key, 0, -1);
        result.put("trim", redisVal.toString());


        String pop = listBean.lpop(key);
        result.put("pop", pop + " == 67");
        result.put("size", listBean.size(key) + "==3");


        listBean.set(key, 0, "aaa");
        result.put("afterSet", listBean.index(key, 0) + "==aaa");
        return JSONObject.toJSONString(result);
    }

    @Autowired
    private PubSubBean pubSubBean;

    @GetMapping(path = "pub")
    public String pubTest(String key, String value) {
        pubSubBean.publish(key, value);
        return "over";
    }

    @GetMapping(path = "sub")
    public String subscribe(String key, String uuid) {
        pubSubBean.subscribe(new MessageListener() {
            @Override
            public void onMessage(Message message, byte[] bytes) {
                System.out.println(uuid + " ==> msg:" + message);
            }
        }, key);
        return "over";
    }
}
