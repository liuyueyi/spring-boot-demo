package com.git.hui.boot.web.converter;

import com.git.hui.boot.web.model.ApiItem;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by @author yihui in 09:28 19/12/25.
 */
public class ApiConverter {

    public static ApiItem convert(String api) {
        ApiItem apiItem = new ApiItem();
        if (StringUtils.isBlank(api)) {
            return apiItem;
        }

        String[] cells = StringUtils.split(api, ".");
        apiItem.setHigh(Integer.parseInt(cells[0]));
        if (cells.length > 1) {
            apiItem.setMid(Integer.parseInt(cells[1]));
        }

        if (cells.length > 2) {
            apiItem.setLow(Integer.parseInt(cells[2]));
        }
        return apiItem;
    }

}
