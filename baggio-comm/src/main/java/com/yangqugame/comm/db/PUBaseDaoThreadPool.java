package com.yangqugame.comm.db;


import com.yangqugame.comm.db.callable.PUExecuteCallable;
import com.yangqugame.comm.db.callable.PUExecuteForObjectCallable;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by phiau on 2017/10/25 0025.
 */
public class PUBaseDaoThreadPool {

    public static boolean execute(PUDatebasePool dbPool, String sql, Object... parameters) {
        try {
            Future<Boolean> future = dbPool.getThreadPoolExecutor().submit(new PUExecuteCallable(dbPool, sql, PUBaseDaoLocalThread.execute, parameters));
            Boolean result = future.get();
            if (null != result) return result.booleanValue();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int executeUpdate(PUDatebasePool dbPool, String sql, Object... parameters) {
        try {
            Future<Integer> future = dbPool.getThreadPoolExecutor().submit(new PUExecuteCallable(dbPool, sql, PUBaseDaoLocalThread.executeUpdate, parameters));
            Integer result = future.get();
            if (null != result) return result.intValue();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static int executeWithGenKey(PUDatebasePool dbPool, String sql, Object... parameters) {
        try {
            Future<Integer> future = dbPool.getThreadPoolExecutor().submit(new PUExecuteCallable(dbPool, sql, PUBaseDaoLocalThread.executeWithGenKey, parameters));
            Integer result = future.get();
            if (null != result) return result.intValue();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static <T> T queryForObject(PUDatebasePool dbPool, String sql, ResultSetHandler<T> rowHandler, Object... parameters) {
        List<T> list = queryForList(dbPool, sql, rowHandler, 1, parameters);
        if (null == list || 0 >= list.size()) return null;
        return list.get(0);
    }

    public static <T> List<T> queryForList(PUDatebasePool dbPool, String sql, ResultSetHandler<T> rowHandler, int limitMaxRows, Object... parameters) {
        try {
            Future<T> future = dbPool.getThreadPoolExecutor().submit(new PUExecuteForObjectCallable(dbPool, sql, rowHandler, limitMaxRows, parameters));
            return (List<T>) future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Integer queryForInteger(PUDatebasePool dbPool, String sql, Object ...parameters){
        return  queryForObject(dbPool, sql,rs->rs.getInt(1), parameters);
    }
}

