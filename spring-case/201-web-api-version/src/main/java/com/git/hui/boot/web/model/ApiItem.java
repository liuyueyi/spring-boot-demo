package com.git.hui.boot.web.model;

import lombok.Data;

/**
 * Created by @author yihui in 17:52 19/12/24.
 */
@Data
public class ApiItem implements Comparable<ApiItem> {

    private int high = 1;

    private int mid = 0;

    private int low = 0;

    public ApiItem() {
    }

    @Override
    public int compareTo(ApiItem right) {
        if (this.getHigh() > right.getHigh()) {
            return 1;
        } else if (this.getHigh() < right.getHigh()) {
            return -1;
        }

        if (this.getMid() > right.getMid()) {
            return 1;
        } else if (this.getMid() < right.getMid()) {
            return -1;
        }

        if (this.getLow() > right.getLow()) {
            return 1;
        } else if (this.getLow() < right.getLow()) {
            return -1;
        }
        return 0;
    }
}
