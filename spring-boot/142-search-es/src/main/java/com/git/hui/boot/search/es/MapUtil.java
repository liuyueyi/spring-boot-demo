package com.git.hui.boot.search.es;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yihui
 * @date 2022/3/3
 */
public class MapUtil {
    public static <K, V> Map<K, V> newMap(K k, V v, Object... kv) {
        Map<K, V> map = new HashMap<>();
        map.put(k, v);
        for (int i = 0; i < kv.length; i += 2) {
            map.put((K) kv[i], (V) kv[i + 1]);
        }
        return map;
    }
}
