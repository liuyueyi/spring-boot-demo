package com.git.hui.boot.influx.client.converter.series;

/**
 * Created by @author yihui in 10:17 19/7/16.
 */
public class BooleanSeriesConverter implements SeriesConverter<Boolean> {
    @Override
    public boolean enabled(Class<?> fieldType, Object... args) {
        return boolean.class.isAssignableFrom(fieldType) || Boolean.class.isAssignableFrom(fieldType);
    }

    @Override
    public int order() {
        return 12;
    }

    @Override
    public Boolean convert(Object s) {
        return Boolean.valueOf(String.valueOf(s));
    }
}
