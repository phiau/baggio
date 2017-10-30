package com.yangqugame.comm.db;

import com.yangqugame.comm.config.DbConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by phiau on 2017/10/25 0025.
 */
public class PUDatebasePoolHikari implements PUDatebasePool {

    private HikariDataSource ds;
    private ThreadPoolExecutor threadPoolExecutor;
    private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    public PUDatebasePoolHikari(DbConfig dbConfig) {
        String url = String.format("jdbc:mysql://%s:%s/%s?characterEncoding=%s&autoReconnect=true&zeroDateTimeBehavior=convertToNull",
                dbConfig.getHost(), dbConfig.getPort(), dbConfig.getDatebase(), dbConfig.getCharSet());

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(dbConfig.getUser());
        config.setPassword(dbConfig.getPsw());
        config.setMaximumPoolSize(dbConfig.getMaxConn());
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);

        this.threadPoolExecutor = new ThreadPoolExecutor(dbConfig.getMaxConn(), dbConfig.getMaxConn(), 20, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    }

    @Override
    public void close() {
        if (null != ds) {
            ds.close();
        }
    }

    @Override
    public Connection getConnection() {
        try {
            if (null == threadLocal.get()) threadLocal.set(this.ds.getConnection());
            return threadLocal.get();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ThreadPoolExecutor getThreadPoolExecutor() {
        return this.threadPoolExecutor;
    }
}
