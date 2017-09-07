package com.yangqugame.global;

/**
 * Created by Administrator on 2017/9/7 0007.
 */
public class BaseConfig {
    private static short serverId = 1;
    private static byte status = 0;
    private static String dbConfigFile = null;
    private static String verifyServerUrl = null;

    public static short getServerId() {
        return serverId;
    }

    public static void setServerId(short serverId) {
        BaseConfig.serverId = serverId;
    }

    public static byte getStatus() {
        return status;
    }

    public static void setStatus(byte status) {
        BaseConfig.status = status;
    }

    public static String getDbConfigFile() {
        return dbConfigFile;
    }

    public static void setDbConfigFile(String dbConfigFile) {
        BaseConfig.dbConfigFile = dbConfigFile;
    }

    public static String getVerifyServerUrl() {
        return verifyServerUrl;
    }

    public static void setVerifyServerUrl(String verifyServerUrl) {
        BaseConfig.verifyServerUrl = verifyServerUrl;
    }

    public static String info() {
        return "BaseConfig{" +
                "serverId=" + serverId +
                ", status=" + status +
                ", dbConfigFile='" + dbConfigFile + '\'' +
                ", verifyServerUrl='" + verifyServerUrl + '\'' +
                '}';
    }
}
