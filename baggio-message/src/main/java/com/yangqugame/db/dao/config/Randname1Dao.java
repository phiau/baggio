package com.yangqugame.db.dao.config;

import com.yangqugame.db.DBPoolMgr;
import com.yangqugame.db.entry.config.Randname1;
import jazmin.driver.jdbc.QueryTerms;
import jazmin.driver.jdbc.SmartBeanDAO;

import java.util.List;

/**
 * Created by phiau on 2017/9/12 0012.
 */
public class Randname1Dao extends SmartBeanDAO<Randname1> {

    public Randname1Dao() {
        setTableName("randname1");
        setTableNamePrefix("");
        setConnectionDriver(DBPoolMgr.getDriverConfig());
        getConnectionDriver().startTransaction(false);
    }

    @Override
    protected List<Randname1> queryList(QueryTerms qt, String... excludeProperties) {
        return super.queryList(qt, excludeProperties);
    }

    public List<Randname1> queryList() {
        QueryTerms qt = new QueryTerms();
        return queryList(qt);
    }
}
