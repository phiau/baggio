package com.yangqugame.db.dao.config;

import com.yangqugame.db.DBPoolMgr;
import com.yangqugame.db.entry.config.SystemConfig;
import jazmin.driver.jdbc.QueryTerms;
import jazmin.driver.jdbc.SmartBeanDAO;

import java.util.List;

/**
 * Created by Administrator on 2017/9/6 0006.
 */
public class SystemConfigDao extends SmartBeanDAO<SystemConfig> {

    public SystemConfigDao() {
        setTableName("ConfigManager");
        setTableNamePrefix("");
        setConnectionDriver(DBPoolMgr.getDriverConfig());
        getConnectionDriver().startTransaction(false);
    }

    @Override
    protected List<SystemConfig> queryList(QueryTerms qt, String... excludeProperties) {
        return super.queryList(qt, excludeProperties);
    }

    public List<SystemConfig> queryList() {
        return queryList(new QueryTerms());
    }

    @Override
    protected int insert(SystemConfig o, boolean withGenerateKey, String... excludeProperties) {
        return super.insert(o, withGenerateKey, excludeProperties);
    }
}
