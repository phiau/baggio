package com.yangqugame.global;

import com.yangqugame.db.dao.data.UserInfoDao;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 服务器运行的一些全局属性
 * Created by Administrator on 2017/9/6 0006.
 */
public class ServerRuntime {

    public final static byte SERVER_STATUS_UNNORMAL = 0;
    public final static byte SERVER_STATUS_NORMAL = 1;

    // 服务器状态，1 正常，非 1 系统维护或者启动中
    private static byte status = SERVER_STATUS_UNNORMAL;

    // 最后一个角色 id
    private static AtomicLong lastUserId;

    public static void initLastUserId(long lastId) {
        if (0 == lastId) {
            String tmp = String.format("%d%02d%06d", BaseConfig.getServerId(), 0, 0);
            lastId = Long.parseLong(tmp);
        }
        lastUserId = new AtomicLong(lastId);
    }

    private static void initLastUserId() {
        long id = UserInfoDao.maxUserId();
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
        status = SERVER_STATUS_NORMAL;
    }
}
