package com.yangqugame.db.dao.data;

import com.yangqugame.db.DBPoolMgr;
import com.yangqugame.db.entry.data.RoleInfo;
import jazmin.driver.jdbc.QueryTerms;
import jazmin.driver.jdbc.SmartBeanDAO;

import java.util.List;

/**
 * Created by Administrator on 2017/9/6 0006.
 */
public class RoleInfoDao extends SmartBeanDAO<RoleInfo> {

    public RoleInfoDao() {
        setTableName("RoleInfo");
        setTableNamePrefix("");
        setConnectionDriver(DBPoolMgr.getDriverData());
        getConnectionDriver().startTransaction(false);
    }

    @Override
    protected RoleInfo query(QueryTerms qt, String... excludeProperties) {
        return super.query(qt, excludeProperties);
    }

    public RoleInfo query(int accountId) {
        QueryTerms qt = new QueryTerms();
        qt.where("accountId", accountId);
        return query(qt);
    }

    @Override
    protected List<RoleInfo> queryList(QueryTerms qt, String... excludeProperties) {
        return super.queryList(qt, excludeProperties);
    }

    public List<RoleInfo> queryList(int accountId) {
        QueryTerms qt = new QueryTerms();
        qt.where("accountId", accountId);
        return queryList(qt);
    }

    @Override
    protected int insert(RoleInfo o, boolean withGenerateKey, String... excludeProperties) {
        return super.insert(o, withGenerateKey, excludeProperties);
    }

    public boolean insert(RoleInfo o) {
        try {
            insert(o, false);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
