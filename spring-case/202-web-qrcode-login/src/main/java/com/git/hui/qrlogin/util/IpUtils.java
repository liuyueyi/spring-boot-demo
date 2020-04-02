package com.git.hui.qrlogin.util;

/**
 * Created by @author yihui in 22:00 20/4/1.
 */

import java.net.*;
import java.util.Enumeration;

public class IpUtils {
    public static final String DEFAULT_IP = "127.0.0.1";

    /**
     * 直接根据第一个网卡地址作为其内网ipv4地址，避免返回 127.0.0.1
     *
     * @return
     */
    public static String getLocalIpByNetcard() {
        try {
            for (Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces(); e.hasMoreElements(); ) {
                NetworkInterface item = e.nextElement();
                for (InterfaceAddress address : item.getInterfaceAddresses()) {
                    if (item.isLoopback() || !item.isUp()) {
                        continue;
                    }
                    if (address.getAddress() instanceof Inet4Address) {
                        Inet4Address inet4Address = (Inet4Address) address.getAddress();
                        return inet4Address.getHostAddress();
                    }
                }
            }
            return InetAddress.getLocalHost().getHostAddress();
        } catch (SocketException | UnknownHostException e) {
            return DEFAULT_IP;
        }
    }

    private static volatile String ip;

    public static String getLocalIP() {
        if (ip == null) {
            synchronized (IpUtils.class) {
                if (ip == null) {
                    ip = getLocalIpByNetcard();
                }
            }
        }
        return ip;
    }
}
