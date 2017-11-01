package com.yangqugame.global;

import com.yangqugame.comm.db.PUBaseDaoThreadPool;
import com.yangqugame.db.DBManager;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 服务器运行的一些全局属性
 * Created by Administrator on 2017/9/6 0006.
 */
public class ServerRuntime {

    // 服务器状态，1 正常，非 1 系统维护或者启动中
    private static byte status = 0;

    // 最后一个角色 id
    private static AtomicLong lastUserId;

    public static void initLastUserId(long lastId) {
        lastUserId = new AtomicLong(lastId);
    }

    private static void initLastUserId() {
        long id = PUBaseDaoThreadPool.queryForInteger(DBManager.getUserPool(), "SELECT MAX(roleId) FROM `userinfo`;");
        ServerRuntime.initLastUserId(id);
    }

    public static long getNewUserId() {
        if (null == lastUserId) {
            initLastUserId(0);
        }
        return lastUserId.getAndIncrement();
    }

    public static byte getStatus() {
        return status;
    }

    public static void init() {
        initLastUserId();
    }
}
