package com.yangqugame.global;

import com.yangqugame.db.DBPoolMgr;
import com.yangqugame.user.Role;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 服务器运行的一些全局属性
 * Created by Administrator on 2017/9/6 0006.
 */
public class ServerRuntime {

    // 服务器状态，1 正常，非 1 系统维护或者启动中
    private static byte status = 0;

    // 最后一个角色 id
    private static AtomicLong lastRoleId;

    public static void initLastRoleId(long lastId) {
        lastRoleId = new AtomicLong(lastId);
    }

    private static void initLastRoleId() {
        try {
            DBPoolMgr.getDriverData().executeQuery("SELECT MAX(roleId) FROM `roleinfo`;", (meta, set) -> {
                try {
                    while (set.next()) {
                        long id = set.getLong(1);
                        id = Role.getPartRoleId(id);
                        ServerRuntime.initLastRoleId(id);
                        break;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long getNewRoleId() {
        if (null == lastRoleId) {
            initLastRoleId(0);
        }
        return lastRoleId.getAndIncrement();
    }

    public static byte getStatus() {
        return status;
    }

    public static void init() {
        initLastRoleId();
    }
}
