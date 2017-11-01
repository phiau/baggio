package com.yangqugame.global;

import com.yangqugame.db.dao.config.Randname1Dao;
import com.yangqugame.db.dao.config.Randname2Dao;
import com.yangqugame.db.dao.config.RoleInfoDao;
import com.yangqugame.db.dao.config.SystemConfigDao;
import com.yangqugame.db.entry.config.Randname1;
import com.yangqugame.db.entry.config.Randname2;
import com.yangqugame.db.entry.config.RoleInfo;
import com.yangqugame.db.entry.config.SystemConfig;

import java.util.*;

/**
 * 数据库配置表，相关业务要考虑重新加载配置
 * Created by phiau on 2017/9/12 0012.
 */
public class TableConfigs {

    private static boolean isLoadFinish = false;
    private static List<Randname1> randname1s = new ArrayList<>();
    private static List<Randname2> randname2s = new ArrayList<>();
    private static List<SystemConfig> systemConfigs = new ArrayList<>();
    private static List<RoleInfo> roleInfos = new ArrayList<>();

    // ----------------------------------------------------------
    private static Set<Integer> createRoleConf = new HashSet<>();
    private static Map<String, String> systemConfigMap = new HashMap<>();
    private static Map<Integer, RoleInfo> roleInfoMap = new HashMap<>();

    public static void load() {
        if (!isLoadFinish) {
            reload();
        }
    }

    public static void reload() {
        isLoadFinish = false;
        randname1s = new Randname1Dao().queryList();
        randname2s = new Randname2Dao().queryList();
        systemConfigs = new SystemConfigDao().queryList();
        roleInfos = new RoleInfoDao().queryList();
        isLoadFinish = true;
        loaded();
    }

    public static void loaded() {
        // 可以在这里调用相关对表的处理
        // 对键值对配置做处理
        systemConfigMap.clear();
        for (SystemConfig config : systemConfigs) {
            systemConfigMap.put(config.getKey(), config.getValues());
        }
        createRoleConf.clear();
        if (systemConfigMap.containsKey("createRole")) {
            String[] crs = systemConfigMap.get("createRole").split("\\|");
            if (null != crs || 0 < crs.length) {
                for (String cr : crs) {
                    createRoleConf.add(Integer.parseInt(cr));
                }
            }
        }
        if (null == createRoleConf || 0 >= createRoleConf.size()) {
            System.exit(1);
        }
        // 球员配置
        for (RoleInfo info : roleInfos) {
            roleInfoMap.put(info.getRoletype(), info);
        }
    }

    // ======================   ======================
    public static boolean existRoleTypeFirstLogin(int roleType) {
        return createRoleConf.contains(roleType);
    }

    public static boolean existRoleType(int roleType) {
        return roleInfoMap.containsKey(roleType);
    }

    // ====================== get & set ======================
    public static boolean isIsLoadFinish() {
        return isLoadFinish;
    }

    public static void setIsLoadFinish(boolean isLoadFinish) {
        TableConfigs.isLoadFinish = isLoadFinish;
    }

    public static List<Randname1> getRandname1s() {
        return randname1s;
    }

    public static void setRandname1s(List<Randname1> randname1s) {
        TableConfigs.randname1s = randname1s;
    }

    public static List<Randname2> getRandname2s() {
        return randname2s;
    }

    public static void setRandname2s(List<Randname2> randname2s) {
        TableConfigs.randname2s = randname2s;
    }

    public static List<SystemConfig> getSystemConfigs() {
        return systemConfigs;
    }

    public static void setSystemConfigs(List<SystemConfig> systemConfigs) {
        TableConfigs.systemConfigs = systemConfigs;
    }
}
