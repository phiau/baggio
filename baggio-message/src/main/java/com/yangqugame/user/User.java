package com.yangqugame.user;

import com.yangqugame.db.dao.data.UserInfoDao;
import com.yangqugame.db.entry.config.Randname1;
import com.yangqugame.db.entry.config.Randname2;
import com.yangqugame.db.entry.data.UserInfo;
import com.yangqugame.global.BaseConfig;
import com.yangqugame.global.ServerRuntime;
import com.yangqugame.global.TableConfigs;
import com.yangqugame.message.SessionManager;
import com.yangqugame.message.bean.ResLogin;
import com.yangqugame.message.MessageSender;
import com.yangqugame.utils.NumberUtils;
import jazmin.server.protobuf.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/6 0006.
 */
public class User {

    public static long genUserId() {
        return ServerRuntime.getNewUserId();
    }

    private static UserInfo initUserInfo(int accountId, long userId, String nickName) {
        UserInfo info = new UserInfo();
        info.setUserid(userId);
        info.setAccountid(accountId);
        info.setNickname(nickName);
        info.setFace("");
        info.setExp(0);
        info.setLevel((short) 0);
        info.setVip((short) 0);
        return info;
    }

    // 初始化角色
    public static UserInfo createUser(Context context, String nickName) {
        int accountId = SessionManager.getAccountBySession(context.getSession());
        if (0 < accountId) {
            if (UserManager.existAccountAnyUser(accountId)) {
                // 已经有角色了
                return null;
            }
            UserInfo userInfo = initUserInfo(accountId, genUserId(), nickName);
            boolean success = new UserInfoDao().insert(userInfo);
            if (success) {
                // 绑定角色，然后通知客户端
                addAccountUserInfo(accountId, userInfo);
                UserManager.accountBindUser(accountId, userInfo);

                ResLogin resLogin = new ResLogin();
                resLogin.setUserNum(1);
                resLogin.setUserList(new ArrayList<>());
                resLogin.getUserList().add(userInfo);
                MessageSender.sendByAccountId(accountId, resLogin);
                return userInfo;
            }
        } else {
            // 需要先登录成功
        }
        return null;
    }

    private static void addAccountUserInfo(int accountId, UserInfo info) {
        UserManager.addAccountUserId(accountId, info.getUserid());
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
            List<UserInfo> userInfos = new UserInfoDao().queryList(accountId);
            if (null == userInfos || 0 == userInfos.size()) {
                // 服务器自动创建一个角色
                createUser(context, genRandName());
                return;
            } else {
                resLogin.setUserNum(userInfos.size());
                resLogin.setUserList(userInfos);

                // 只有一个角色，直接绑定 account id 为该角色
                if (1 == userInfos.size()) {
                    UserManager.accountBindUser(accountId, userInfos.get(0));
                }
                for (UserInfo info : userInfos) {
                    addAccountUserInfo(accountId, info);
                }
            }
            MessageSender.sendByAccountId(accountId, resLogin);
        } else {
            resLogin.setUserNum(-1);
            MessageSender.send(context, resLogin);
        }
    }

    public static void verifyAction(Context context, int accountId) {
        if (0 < accountId) {
            SessionManager.bindAccountSession(context, accountId);
        }
        readyLogin(context, accountId);
    }

    public static void createFirstRole(Context context, int roleId) {
        // 判断玩家是不是还没有球员
    }
}
