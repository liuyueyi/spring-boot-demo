package com.git.hui.boot.redis.demo;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by @author yihui in 18:05 18/11/6.
 */
@Component
public class ListBean {

    private RedisTemplate<String, String> redisTemplate;

    public ListBean(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 删除缓存
     *
     * @param key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }


    /**
     * 在列表的最左边塞入一个value
     *
     * @param key
     * @param value
     */
    public void lpush(String key, String value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 获取指定索引位置的值, index为-1时，表示返回的是最后一个；当index大于实际的列表长度时，返回null
     *
     * @param key
     * @param index
     * @return
     */
    public String index(String key, int index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 获取范围值，闭区间，start和end这两个下标的值都会返回; end为-1时，表示获取的是最后一个；
     *
     * 如果希望返回最后两个元素，可以传入  -2, -1
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> range(String key, int start, int end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 弹出最左边的值，lpush配合lpop使用时，list模拟的将是栈的数据结构；lpush配合rpop使用时，模拟的则是队列的数据结构
     *
     * @param key
     * @return
     */
    public String lpop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }


    /**
     * 返回列表的长度
     *
     * @param key
     * @return
     */
    public Long size(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 设置list中指定下标的值，采用干的是替换规则, 最左边的下标为0；-1表示最右边的一个
     *
     * @param key
     * @param index
     * @param value
     */
    public void set(String key, Integer index, String value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 删除list首尾，只保留 [start, end] 之间的值
     *
     * @param key
     * @param start
     * @param end
     */
    public void trim(String key, Integer start, Integer end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    /**
     * 删除列表中值为value的元素，总共删除count次；
     *
     * 如原来列表为 【1， 2， 3， 4， 5， 2， 1， 2， 5】
     * 传入参数 value=2, count=1 表示删除一个列表中value为2的元素
     * 则执行后，列表为 【1， 3， 4， 5， 2， 1， 2， 5】
     *
     * @param key
     * @param value
     * @param count
     */
    public void remove(String key, String value, int count) {
        redisTemplate.opsForList().remove(key, count, value);
    }
}
