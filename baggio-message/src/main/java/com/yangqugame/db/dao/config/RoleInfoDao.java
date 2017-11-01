package com.yangqugame.db.dao.config;

import com.yangqugame.comm.db.PUQueryTerms;
import com.yangqugame.comm.db.PUSmartBeanDAO;
import com.yangqugame.db.DBManager;
import com.yangqugame.db.entry.config.RoleInfo;

import java.util.List;

/**
 * Created by phiau on 2017/11/1 0001.
 */
public class RoleInfoDao extends PUSmartBeanDAO<RoleInfo> {

    public RoleInfoDao() {
        super("", "roleinfo", DBManager.getSystemPool());
    }

    public List<RoleInfo> queryList() {
        return queryList(new PUQueryTerms());
    }
}
