package com.git.hui.boot.redis.site.util;

import java.time.LocalDate;

/**
 * Created by @author yihui in 18:03 19/5/12.
 */
public class DateUtil {

    public static String getToday() {
        LocalDate date = LocalDate.now();
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        StringBuilder buf = new StringBuilder(8);
        return buf.append(year).append(month < 10 ? "0" : "").append(month).append(day < 10 ? "0" : "").append(day)
                .toString();
    }

}
