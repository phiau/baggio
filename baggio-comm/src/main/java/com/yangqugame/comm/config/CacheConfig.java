package com.yangqugame.comm.config;

/**
 * Created by phiau on 2017/10/27 0027.
 */
public class CacheConfig {
    private static String host;
    private static String psw;
    private static int port;

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        CacheConfig.host = host;
    }

    public static String getPsw() {
        return psw;
    }

    public static void setPsw(String psw) {
        CacheConfig.psw = psw;
    }

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        CacheConfig.port = port;
    }

    public static String info() {
        return "CacheConfig{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}
