package com.yangqugame.db.dao.data;

import com.yangqugame.comm.db.PUQueryTerms;
import com.yangqugame.comm.db.PUSmartBeanDAO;
import com.yangqugame.db.DBManager;
import com.yangqugame.db.entry.data.RoleInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/9/6 0006.
 */
public class RoleInfoDao extends PUSmartBeanDAO<RoleInfo> {

    public RoleInfoDao() {
        super("", "RoleInfo", DBManager.getUserPool());
    }

    @Override
    protected RoleInfo query(PUQueryTerms qt, String... excludeProperties) {
        return super.query(qt, excludeProperties);
    }

    public RoleInfo query(int accountId) {
        PUQueryTerms qt = new PUQueryTerms();
        qt.where("accountId", accountId);
        return query(qt);
    }

    @Override
    protected List<RoleInfo> queryList(PUQueryTerms qt, String... excludeProperties) {
        return super.queryList(qt, excludeProperties);
    }

    public List<RoleInfo> queryList(int accountId) {
        PUQueryTerms qt = new PUQueryTerms();
        qt.where("accountId", accountId);
        return queryList(qt);
    }

    @Override
    protected int insert(RoleInfo o, boolean withGenerateKey, String... excludeProperties) {
        return super.insert(o, withGenerateKey, excludeProperties);
    }

    public int max() {
        return 0;
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
