package com.yangqugame.global;

import com.yangqugame.db.dao.config.Randname1Dao;
import com.yangqugame.db.dao.config.Randname2Dao;
import com.yangqugame.db.entry.config.Randname1;
import com.yangqugame.db.entry.config.Randname2;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库配置表，相关业务要考虑重新加载配置
 * Created by phiau on 2017/9/12 0012.
 */
public class TableConfigs {

    private static boolean isLoadFinish = false;
    private static List<Randname1> randname1s = new ArrayList<>();
    private static List<Randname2> randname2s = new ArrayList<>();

    public static void load() {
        if (!isLoadFinish) {
            reload();
        }
    }

    public static void reload() {
        isLoadFinish = false;
        randname1s = new Randname1Dao().queryList();
        randname2s = new Randname2Dao().queryList();
        isLoadFinish = true;
        loaded();
    }

    public static void loaded() {
        // 可以在这里调用相关对表的处理
    }

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
}
