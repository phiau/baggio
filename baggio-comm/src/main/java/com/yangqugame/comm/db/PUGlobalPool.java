package com.yangqugame.comm.db;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by phiau on 2017/10/25 0025.
 */
public class PUGlobalPool {

    private static final String dbUrl = "jdbc:mysql://%s:%s/%s?characterEncoding=%s&autoReconnect=true&zeroDateTimeBehavior=convertToNull";
    private static int configPoolNum = 2;
    private static PUDataDBPool dbPool;
    private static PUDataDBPool configPool;
    private static ThreadPoolExecutor dataThreadPool = null;
    private static ThreadPoolExecutor configThreadPool = null;

    public static void initThreadPool(String host, String port, String uname, String psw, String dataDbName, String confDbName, int maxConn, String charSet) {
        if (null == dataThreadPool) {
            dataThreadPool = new ThreadPoolExecutor(maxConn, maxConn, 20, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        }
        if (null == configThreadPool) {
            configThreadPool = new ThreadPoolExecutor(configPoolNum, configPoolNum, 20, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        }
        if (null == dbPool) {
            String url = String.format(dbUrl, host, port, dataDbName, charSet, uname, psw);
            dbPool = new PUDataDBPool(url, uname, psw, maxConn, "com.mysql.jdbc.Driver");
        }
        if (null == configPool) {
            String url = String.format(dbUrl, host, port, confDbName, charSet, uname, psw);
            configPool = new PUDataDBPool(url, uname, psw, configPoolNum, "com.mysql.jdbc.Driver");
        }
    }

    public static PUDataDBPool getDbPool() {
        return dbPool;
    }

    public static PUDataDBPool getConfigPool() {
        return configPool;
    }

    public static ThreadPoolExecutor getDataThreadPool() {
        return dataThreadPool;
    }

    public static ThreadPoolExecutor getConfigThreadPool() {
        return configThreadPool;
    }
}
