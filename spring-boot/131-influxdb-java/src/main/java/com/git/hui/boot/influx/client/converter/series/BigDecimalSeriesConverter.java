package com.git.hui.boot.influx.client.converter.series;

import java.math.BigDecimal;

/**
 * Created by @author yihui in 10:24 19/7/16.
 */
public class BigDecimalSeriesConverter implements SeriesConverter<BigDecimal> {
    @Override
    public boolean enabled(Class<?> fieldType, Object... args) {
        return BigDecimal.class.isAssignableFrom(fieldType);
    }

    @Override
    public int order() {
        return 0;
    }

    @Override
    public BigDecimal convert(Object s) {
        return new BigDecimal(String.valueOf(s));
    }
}
