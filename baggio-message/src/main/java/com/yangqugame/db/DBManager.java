package com.yangqugame.db;

import com.yangqugame.comm.config.DbConfig;
import com.yangqugame.comm.db.PUDatebasePool;
import com.yangqugame.comm.db.PUDatebasePoolManager;
import com.yangqugame.comm.util.PropertiesConfigUtil;

/**
 * Created by phiau on 2017/10/30 0030.
 */
public class DBManager {

    private static DbConfig userDBConfig = null;
    private static DbConfig systemDbConfig = null;

    public static void init(String userConfFile, String systemConfFile) {
        userDBConfig = (DbConfig) PropertiesConfigUtil.file2ClassInstance(userConfFile, DbConfig.class);
        PUDatebasePoolManager.addDatebasePool(userDBConfig.getDatebase(), userDBConfig);
        systemDbConfig = (DbConfig) PropertiesConfigUtil.file2ClassInstance(systemConfFile, DbConfig.class);
        PUDatebasePoolManager.addDatebasePool(systemDbConfig.getDatebase(), systemDbConfig);
    }

    public static String getUserDbName() {
        return userDBConfig.getDatebase();
    }

    public static String getSystemDbName() {
        return systemDbConfig.getDatebase();
    }

    public static PUDatebasePool getUserPool() {
        return PUDatebasePoolManager.getDatebasePool(userDBConfig.getDatebase());
    }

    public static PUDatebasePool getSystemPool() {
        return PUDatebasePoolManager.getDatebasePool(systemDbConfig.getDatebase());
    }
}
