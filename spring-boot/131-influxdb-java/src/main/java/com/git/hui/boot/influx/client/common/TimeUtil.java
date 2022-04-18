package com.git.hui.boot.influx.client.common;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * Created by @author yihui in 11:31 19/7/16.
 */
public class TimeUtil {
    public static final DateTimeFormatter RFC3339_FORMATTER =
            (new DateTimeFormatterBuilder()).appendPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true).appendZoneOrOffsetId().toFormatter();

    public static Long str2timestamp(String s) {
        Instant instant = Instant.from(TimeUtil.RFC3339_FORMATTER.parse(String.valueOf(s)));
        return instant.getEpochSecond() * 1000L + instant.getNano() / 1000000L;
    }
}
