package com.yangqugame.db.dao.config;

import com.yangqugame.comm.db.PUQueryTerms;
import com.yangqugame.comm.db.PUSmartBeanDAO;
import com.yangqugame.db.DBManager;
import com.yangqugame.db.entry.config.SystemConfig;

import java.util.List;

/**
 * Created by Administrator on 2017/9/6 0006.
 */
public class SystemConfigDao extends PUSmartBeanDAO<SystemConfig> {

    public SystemConfigDao() {
        super("", "systemconfig", DBManager.getSystemPool());
    }

    public List<SystemConfig> queryList() {
        return queryList(new PUQueryTerms());
    }
}
