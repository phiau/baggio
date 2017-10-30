package com.yangqugame.comm.config;

/**
 *  连接数据库配置
 * Created by phiau on 2017/10/27 0027.
 */
public class DbConfig {
    private static String host;
    private static String port;
    private static String user;
    private static String psw;
    private static String dataDbName;
    private static String confDbName;
    private static String charSet;
    private static int maxConn = 10;

    public static String getHost() {
        return host;
    }

    public static void setHost(String host) {
        DbConfig.host = host;
    }

    public static String getPort() {
        return port;
    }

    public static void setPort(String port) {
        DbConfig.port = port;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        DbConfig.user = user;
    }

    public static String getPsw() {
        return psw;
    }

    public static void setPsw(String psw) {
        DbConfig.psw = psw;
    }

    public static String getDataDbName() {
        return dataDbName;
    }

    public static void setDataDbName(String dataDbName) {
        DbConfig.dataDbName = dataDbName;
    }

    public static String getConfDbName() {
        return confDbName;
    }

    public static void setConfDbName(String confDbName) {
        DbConfig.confDbName = confDbName;
    }

    public static String getCharSet() {
        return charSet;
    }

    public static void setCharSet(String charSet) {
        DbConfig.charSet = charSet;
    }

    public static int getMaxConn() {
        return maxConn;
    }

    public static void setMaxConn(int maxConn) {
        DbConfig.maxConn = maxConn;
    }

    public static String info() {
        return "DbConfig{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", user='" + user + '\'' +
                ", dataDbName='" + dataDbName + '\'' +
                ", confDbName='" + confDbName + '\'' +
                ", charSet='" + charSet + '\'' +
                ", maxConn='" + maxConn + '\'' +
                '}';
    }
}
