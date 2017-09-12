package com.yangqugame.user;

import com.yangqugame.db.entry.data.RoleInfo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by phiau on 2017/9/12 0012.
 */
public class RoleManager {
    private static Map<Integer, RoleInfo> roleInfoMap = new ConcurrentHashMap<>();  // key:account id, value:登录的角色信息
    private static Map<Integer, Set> roleIdSetMap = new HashMap<>();  // key:account id, value:角色 id 集合，可能有多个角色

    public static void accountBindRole(int accountId, RoleInfo info) {
        roleInfoMap.put(accountId, info);
    }

    // 该账号是否有角色
    public static boolean existAccountAnyRole(int accountId) {
        if (roleIdSetMap.containsKey(accountId)) {
            return 0 < roleIdSetMap.get(accountId).size();
        }
        return false;
    }

    // 是否有该角色
    public static boolean existAccountRoleId(int accountId, long roleId) {
        if (roleIdSetMap.containsKey(accountId)) {
            return roleIdSetMap.get(accountId).contains(roleId);
        }
        return false;
    }

    // 为账号，增加角色
    public static void addAccountRoleId(int accountId, Set roleIds) {
        if (!roleIdSetMap.containsKey(accountId)) {
            roleIdSetMap.put(accountId, new HashSet());
        }
        roleIdSetMap.get(accountId).addAll(roleIds);
    }

    public static void addAccountRoleId(int accountId, long roleId) {
        if (!roleIdSetMap.containsKey(accountId)) {
            roleIdSetMap.put(accountId, new HashSet());
        }
        roleIdSetMap.get(accountId).add(roleId);
    }
}
