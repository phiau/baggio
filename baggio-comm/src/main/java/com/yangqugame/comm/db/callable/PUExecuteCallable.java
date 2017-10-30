package com.yangqugame.comm.db.callable;


import com.yangqugame.comm.db.PUBaseDaoLocalThread;
import com.yangqugame.comm.db.PUDBPool;

import java.sql.Connection;
import java.util.concurrent.Callable;

/**
 * Created by phiau on 2017/10/25 0025.
 */
public class PUExecuteCallable implements Callable {
    private PUDBPool puDBPool;
    private String sql;
    private short type;
    private Object[] parameters;

    public PUExecuteCallable(PUDBPool puDBPool, String sql, short type, Object... parameters) {
        this.puDBPool = puDBPool;
        this.sql = sql;
        this.type = type;
        this.parameters = parameters;
    }

    @Override
    public Object call() throws Exception {
        Connection conn = puDBPool.getConnection();
        return PUBaseDaoLocalThread.executeBase(conn, sql, type, parameters);
    }
}
