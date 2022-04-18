package com.git.hui.boot.influx.client.converter.series;

import org.springframework.core.convert.converter.Converter;

/**
 * 将influx中返回的字段转换为目标类型
 *
 * Created by @author yihui in 10:12 19/7/16.
 */
public interface SeriesConverter<T> extends Converter<Object, T> {
    /**
     * 根据field类型判断是否适用这个Converter来实现数据转换
     *
     * @param fieldType
     * @param args
     * @return
     */
    boolean enabled(final Class<?> fieldType, Object... args);

    /**
     * 排序，优先级
     *
     * @return
     */
    default int order() {
        return 0;
    }
}
