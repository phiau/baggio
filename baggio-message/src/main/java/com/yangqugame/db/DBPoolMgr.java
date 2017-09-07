package com.yangqugame.db;

import jazmin.driver.jdbc.C3p0ConnectionDriver;

import java.beans.PropertyVetoException;

/**
 * Created by Administrator on 2017/8/31 0031.
 */
public class DBPoolMgr {

    private static final String dbUrl = "jdbc:mysql://%s:%s/%s?characterEncoding=%s&autoReconnect=true&zeroDateTimeBehavior=convertToNull";
    private static C3p0ConnectionDriver driverData;
    private static C3p0ConnectionDriver driverConfig;

    public static boolean init(String address, String port, String user, String psw, String dataDbName, String confDbName, String charSet) {
        try {
            driverData = new C3p0ConnectionDriver();
            driverData.setUrl(String.format(dbUrl, address, port, dataDbName, charSet, user, psw));
            driverData.setUser(user);
            driverData.setPassword(psw);
            driverData.setDriverClass("com.mysql.jdbc.Driver");
            driverData.setInitialPoolSize(5);
            driverData.setMaxPoolSize(10);
            driverData.setMinPoolSize(5);
            driverData.setStatSql(true);
            driverData.start();

            driverConfig = new C3p0ConnectionDriver();
            driverConfig.setUrl(String.format(dbUrl, address, port, confDbName, charSet, user, psw));
            driverConfig.setUser(user);
            driverConfig.setPassword(psw);
            driverConfig.setDriverClass("com.mysql.jdbc.Driver");
            driverConfig.setInitialPoolSize(5);
            driverConfig.setMaxPoolSize(10);
            driverConfig.setMinPoolSize(5);
            driverConfig.setStatSql(true);
            driverConfig.start();
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static C3p0ConnectionDriver getDriverData() {
        return driverData;
    }

    public static C3p0ConnectionDriver getDriverConfig() {
        return driverConfig;
    }

}
