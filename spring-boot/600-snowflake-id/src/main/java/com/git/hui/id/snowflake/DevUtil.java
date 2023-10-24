package com.git.hui.id.snowflake;

import org.springframework.util.StringUtils;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

/**
 * @author YiHui
 * @date 2023/10/23
 */
public class DevUtil {
    public static List<Integer> calculateDefaultInfo(long dataCenterSize, long workerIdSize) {
        String ip = null;
        try {
            ip = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            ip = "127.0.0.1";
        }
        String[] cells = ip.split("\\.");
        // 机房
        int dataCenter = Integer.parseInt(cells[2]) & ((1 << dataCenterSize) - 1);
        // 设备
        int workId = Integer.parseInt(cells[3]) >> 16 & ((1 << workerIdSize) - 1);
        return Arrays.asList(dataCenter, workId);
    }

}
