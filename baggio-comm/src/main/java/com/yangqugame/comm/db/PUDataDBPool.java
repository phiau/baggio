package com.yangqugame.comm.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by phiau on 2017/10/25 0025.
 */
public class PUDataDBPool implements PUDBPool {

    private HikariDataSource ds;
    private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    public PUDataDBPool(String url, String uname, String psw, int maxConn, String driverClassName) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(uname);
        config.setPassword(psw);
        config.setMaximumPoolSize(maxConn);
        config.setDriverClassName(driverClassName);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        ds = new HikariDataSource(config);
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
}
