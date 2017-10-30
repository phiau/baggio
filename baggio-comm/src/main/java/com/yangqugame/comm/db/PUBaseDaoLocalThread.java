package com.yangqugame.comm.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by phiau on 2017/10/26 0026.
 */
public class PUBaseDaoLocalThread {

    public final static short execute = 1;
    public final static short executeUpdate = 2;
    public final static short executeWithGenKey = 3;

    public static Object executeBase(Connection conn, String sql, short type, Object... parameters) {
        PreparedStatement ps = null;
        try {
            if (PUBaseDaoLocalThread.executeWithGenKey == type) {
                ps = conn.prepareStatement(sql, 1);
            } else {
                ps = conn.prepareStatement(sql);
            }
            PUConnectionUtil.set(ps, parameters);
            if (PUBaseDaoLocalThread.execute == type) {
                return ps.execute();
            } else if (PUBaseDaoLocalThread.executeUpdate == type) {
                return ps.executeUpdate();
            } else if (PUBaseDaoLocalThread.executeWithGenKey == type) {
                ps.execute();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            throw new ConnectionException(e);
        } finally {
            PUConnectionUtil.closeStatement(ps);
        }
        return null;
    }

    public static boolean execute(Connection conn, String sql, Object... parameters) {
        Boolean result = (Boolean) executeBase(conn, sql, PUBaseDaoLocalThread.execute, parameters);
        if (null != result) return result.booleanValue();
        return false;
    }

    public static int executeUpdate(Connection conn, String sql, Object... parameters) {
        Integer result = (Integer) executeBase(conn, sql, PUBaseDaoLocalThread.executeUpdate, parameters);
        if (null != result) return result.intValue();
        return -1;
    }

    public static int executeWithGenKey(Connection conn, String sql, Object... parameters) {
        Integer result = (Integer) executeBase(conn, sql, PUBaseDaoLocalThread.executeWithGenKey, parameters);
        if (null != result) return result.intValue();
        return -1;
    }

    public static <T> T queryForObject(Connection conn, String sql, ResultSetHandler<T> rowHandler, Object... parameters) {
        List<T> list = queryForList(conn, sql, rowHandler, 1, parameters);
        if (null == list || 0 >= list.size()) return null;
        return list.get(0);
    }

    public static <T> List<T> queryForList(Connection conn, String sql, ResultSetHandler<T> rowHandler, int limitMaxRows, Object... parameters) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            if(limitMaxRows>0){
                ps.setMaxRows(limitMaxRows);
            }
            PUConnectionUtil.set(ps, parameters);
            rs = ps.executeQuery();
            List<T> result = new ArrayList<T>();
            while (rs.next()) {
                result.add(rowHandler.handleRow(rs));
            }
            return result;
        } catch (Exception e) {
            throw new ConnectionException(e);
        } finally {
            PUConnectionUtil.closeResultSet(rs);
            PUConnectionUtil.closeStatement(ps);
        }
    }

    public static Integer queryForInteger(Connection conn, String sql,Object ...parameters){
        return  queryForObject(conn, sql,rs->rs.getInt(1), parameters);
    }
}
