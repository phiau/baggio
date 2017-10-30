package com.yangqugame.comm.db.callable;


import com.yangqugame.comm.db.PUDatebasePool;
import com.yangqugame.comm.db.PUBaseDaoLocalThread;
import com.yangqugame.comm.db.ResultSetHandler;

import java.sql.Connection;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by phiau on 2017/10/25 0025.
 */
public class PUExecuteForObjectCallable<T> implements Callable {
    private PUDatebasePool puDbPool;
    private String sql;
    private ResultSetHandler<T> rowHandler;
    private int limitMaxRows;
    private Object[] parameters;

    public PUExecuteForObjectCallable(PUDatebasePool puDbPool, String sql, ResultSetHandler<T> rowHandler, int limitMaxRows, Object... parameters) {
        this.puDbPool = puDbPool;
        this.sql = sql;
        this.rowHandler = rowHandler;
        this.limitMaxRows = limitMaxRows;
        this.parameters = parameters;
    }

    @Override
    public List<T> call() throws Exception {
        Connection conn = puDbPool.getConnection();
        return PUBaseDaoLocalThread.queryForList(conn, sql, rowHandler, limitMaxRows, parameters);
    }
}
