package com.yangqugame.comm.db;

import java.sql.Connection;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by phiau on 2017/10/25 0025.
 */
public interface PUDatebasePool {

    public void close();

    public Connection getConnection();

    public ThreadPoolExecutor getThreadPoolExecutor();

}
