package com.yangqugame.global;

/**
 * Created by Administrator on 2017/9/7 0007.
 */
public class DbConfig {
    private static String address;
    private static String port;
    private static String user;
    private static String psw;
    private static String dataDbName;
    private static String confDbName;
    private static String charSet;

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        DbConfig.address = address;
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

    public static String info() {
        return "DbConfig{" +
                "address='" + address + '\'' +
                ", port='" + port + '\'' +
                ", user='" + user + '\'' +
                ", dataDbName='" + dataDbName + '\'' +
                ", confDbName='" + confDbName + '\'' +
                ", charSet='" + charSet + '\'' +
                '}';
    }
}
