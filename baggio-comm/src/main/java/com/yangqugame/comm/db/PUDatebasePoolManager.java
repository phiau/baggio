package com.yangqugame.comm.db;

import com.yangqugame.comm.config.DbConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by phiau on 2017/10/30 0030.
 */
public class PUDatebasePoolManager {

    private static Map<String, PUDatebasePool> datebasePoolMap;

    public static synchronized void addDatebasePool(String name, DbConfig dbConfig) {
        PUDatebasePoolHikari puDatebasePoolHikari = new PUDatebasePoolHikari(dbConfig);
        if (null == datebasePoolMap) {
            datebasePoolMap = new HashMap<>();
        }
        datebasePoolMap.put(name, puDatebasePoolHikari);
    }

    public static PUDatebasePool getDatebasePool(String name) {
        if (null != datebasePoolMap) {
            return datebasePoolMap.get(name);
        }
        return null;
    }
}
