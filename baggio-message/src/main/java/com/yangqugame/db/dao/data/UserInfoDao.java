package com.yangqugame.db.dao.data;

import com.yangqugame.comm.db.PUQueryTerms;
import com.yangqugame.comm.db.PUSmartBeanDAO;
import com.yangqugame.db.DBManager;
import com.yangqugame.db.entry.data.UserInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/9/6 0006.
 */
public class UserInfoDao extends PUSmartBeanDAO<UserInfo> {

    public UserInfoDao() {
        super("", "t_userinfo", DBManager.getUserPool());
    }

    @Override
    protected UserInfo query(PUQueryTerms qt, String... excludeProperties) {
        return super.query(qt, excludeProperties);
    }

    public UserInfo query(int accountId) {
        PUQueryTerms qt = new PUQueryTerms();
        qt.where("accountId", accountId);
        return query(qt);
    }

    @Override
    protected List<UserInfo> queryList(PUQueryTerms qt, String... excludeProperties) {
        return super.queryList(qt, excludeProperties);
    }

    public List<UserInfo> queryList(int accountId) {
        PUQueryTerms qt = new PUQueryTerms();
        qt.where("accountId", accountId);
        return queryList(qt);
    }

    @Override
    protected int insert(UserInfo o, boolean withGenerateKey, String... excludeProperties) {
        return super.insert(o, withGenerateKey, excludeProperties);
    }

    public int max() {
        return 0;
    }

    public boolean insert(UserInfo o) {
        try {
            insert(o, false);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
