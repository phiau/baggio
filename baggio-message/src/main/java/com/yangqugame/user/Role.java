package com.yangqugame.user;

import com.yangqugame.db.dao.data.RoleInfoDao;
import com.yangqugame.db.entry.config.Randname1;
import com.yangqugame.db.entry.config.Randname2;
import com.yangqugame.db.entry.data.RoleInfo;
import com.yangqugame.global.BaseConfig;
import com.yangqugame.global.ServerRuntime;
import com.yangqugame.global.TableConfigs;
import com.yangqugame.message.SessionManager;
import com.yangqugame.msgBean.ResLogin;
import com.yangqugame.msgUtils.MessageSender;
import com.yangqugame.utils.NumberUtils;
import jazmin.server.protobuf.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/6 0006.
 */
public class Role {

    public static long getPartRoleId(long id) {
        return id % (int)(Math.pow(10, 6));
    }

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
    public static RoleInfo createRole(Context context, String nickName) {
        int accountId = SessionManager.getAccountBySession(context.getSession());
        if (0 < accountId) {
            if (RoleManager.existAccountAnyRole(accountId)) {
                // 已经有角色了
                return null;
            }
            RoleInfo roleInfo = initRoleInfo(accountId, genRoleId(), nickName);
            boolean success = new RoleInfoDao().insert(roleInfo);
            if (success) {
                // 绑定角色，然后通知客户端
                addAccountRoleInfo(accountId, roleInfo);
                RoleManager.accountBindRole(accountId, roleInfo);

                ResLogin resLogin = new ResLogin();
                resLogin.setRoleNum(1);
                resLogin.setRoleList(new ArrayList<>());
                resLogin.getRoleList().add(roleInfo);
                MessageSender.send(accountId, resLogin);
                return roleInfo;
            }
        } else {
            // 需要先登录成功
        }
        return null;
    }

    private static void addAccountRoleInfo(int accountId, RoleInfo info) {
        RoleManager.addAccountRoleId(accountId, info.getRoleid());
    }

    private static String genRandName() {
        List<Randname1> nameList1 = TableConfigs.getRandname1s();
        List<Randname2> nameList2 = TableConfigs.getRandname2s();
        String n1 = nameList1.get(NumberUtils.getRandNum(nameList1.size())).getName();
        String n2 = nameList2.get(NumberUtils.getRandNum(nameList2.size())).getName();
        return n1 + n2;
    }

    public static void readyLogin(Context context, int accountId) {
        ResLogin resLogin = new ResLogin();
        if (0 < accountId) {
            List<RoleInfo> roleInfos = new RoleInfoDao().queryList(accountId);
            if (null == roleInfos || 0 == roleInfos.size()) {
                // 服务器自动创建一个角色
                createRole(context, genRandName());
                return;
            } else {
                resLogin.setRoleNum(roleInfos.size());
                resLogin.setRoleList(roleInfos);

                // 只有一个角色，直接绑定 account id 为该角色
                if (1 == roleInfos.size()) {
                    RoleManager.accountBindRole(accountId, roleInfos.get(0));
                }
                for (RoleInfo info : roleInfos) {
                    addAccountRoleInfo(accountId, info);
                }
            }
            MessageSender.send(accountId, resLogin);
        } else {
            resLogin.setRoleNum(-1);
            MessageSender.send(context, resLogin);
        }
    }

    public static void verifyAction(Context context, int accountId) {
        if (0 < accountId) {
            SessionManager.bindAccountSession(context, accountId);
        }
        readyLogin(context, accountId);
    }
}
