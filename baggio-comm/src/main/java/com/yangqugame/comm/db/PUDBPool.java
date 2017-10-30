package com.yangqugame.comm.db;

import java.sql.Connection;

/**
 * Created by phiau on 2017/10/25 0025.
 */
public interface PUDBPool {

    public void close();

    public Connection getConnection();

}
