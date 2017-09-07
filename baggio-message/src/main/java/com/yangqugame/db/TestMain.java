package com.yangqugame.db;

import com.yangqugame.global.ServerRuntime;
import com.yangqugame.user.Role;

/**
 * Created by Administrator on 2017/9/6 0006.
 */
public class TestMain {

    public static void main(String [] argv) {
        DBPoolMgr.init("localhost", "3306", "root", "", "baggio_zone_data", "baggio_zone_config", "utf-8");

        ServerRuntime.initLastRoleId(1);

        Role.createRole(1, "ni");
    }
}
