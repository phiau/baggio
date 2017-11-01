package com.yangqugame.user;

import com.yangqugame.db.entry.data.UserInfo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by phiau on 2017/9/12 0012.
 */
public class UserManager {
    private static Map<Integer, UserInfo> userInfoMap = new ConcurrentHashMap<>();  // key:account id, value:登录的 user 信息
    private static Map<Long, Integer> userId2account = new ConcurrentHashMap<>();
    private static Map<Integer, Set> userIdSetMap = new HashMap<>();  // key:account id, value:角色 id 集合，可能有多个 user

    public static void accountBindUser(int accountId, UserInfo info) {
        userInfoMap.put(accountId, info);
        userId2account.put(info.getUserid(), accountId);
        OnlineUserActorManager.userLogin(info.userid);
    }

    public static UserInfo getUserInfoByAccountId(int accountId) {
        return userInfoMap.get(accountId);
    }

    public static long getUserIdByAccountId(int accountId) {
        if (userInfoMap.containsKey(accountId)) {
            return userInfoMap.get(accountId).userid;
        }
        return 0;
    }

    // 该账号是否有角色
    public static boolean existAccountAnyUser(int accountId) {
        if (userIdSetMap.containsKey(accountId)) {
            return 0 < userIdSetMap.get(accountId).size();
        }
        return false;
    }

    // 是否有该角色
    public static boolean existAccountUserId(int accountId, long userId) {
        if (userIdSetMap.containsKey(accountId)) {
            return userIdSetMap.get(accountId).contains(userId);
        }
        return false;
    }

    // 为账号，增加角色
    public static void addAccountUserId(int accountId, Set roleIds) {
        if (!userIdSetMap.containsKey(accountId)) {
            userIdSetMap.put(accountId, new HashSet());
        }
        userIdSetMap.get(accountId).addAll(roleIds);
    }

    public static void addAccountUserId(int accountId, long userId) {
        if (!userIdSetMap.containsKey(accountId)) {
            userIdSetMap.put(accountId, new HashSet());
        }
        userIdSetMap.get(accountId).add(userId);
    }

    public static int getAccountIdByUserId(long userId) {
        if (userId2account.containsKey(userId)) {
            return userId2account.get(userId);
        }
        return -1;
    }
}
