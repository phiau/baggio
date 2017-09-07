package com.yangqugame.user;

import com.yangqugame.db.dao.data.RoleInfoDao;
import com.yangqugame.db.entry.data.RoleInfo;
import com.yangqugame.global.BaseConfig;
import com.yangqugame.global.ServerRuntime;

/**
 * Created by Administrator on 2017/9/6 0006.
 */
public class Role {

    public static long genRoleId() {
        String tmp = String.format("%d%02d%06d", BaseConfig.getServerId(), 0, ServerRuntime.getNewRoleId());
        return Long.parseLong(tmp);
    }

    private static RoleInfo initRoleInfo(int accountId, long roleId, String nickName) {
        RoleInfo info = new RoleInfo();
        info.setRoleid(roleId);
        info.setAccountid(accountId);
        info.setNickname(nickName);
        info.setFace("");
        info.setExp(0);
        info.setLevel((short) 0);
        info.setVip((short) 0);
        return info;
    }

    // 初始化角色
    public static RoleInfo createRole(int accountId, String nickName) {
        RoleInfo roleInfo = initRoleInfo(accountId, genRoleId(), nickName);
        boolean success = new RoleInfoDao().insert(roleInfo);
        if (success) {
            return roleInfo;
        }
        return null;
    }
}
