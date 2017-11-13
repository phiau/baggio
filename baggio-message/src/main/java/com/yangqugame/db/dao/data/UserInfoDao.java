package com.yangqugame.db.dao.data;

import com.yangqugame.comm.db.PUBaseDaoThreadPool;
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

    public UserInfo query(long userId) {
        PUQueryTerms qt = new PUQueryTerms();
        qt.where("userId", userId);
        return query(qt);
    }

    public List<UserInfo> queryList(int accountId) {
        PUQueryTerms qt = new PUQueryTerms();
        qt.where("accountId", accountId);
        return queryList(qt);
    }

    public static int maxUserId() {
        return PUBaseDaoThreadPool.queryForInteger(DBManager.getUserPool(), "SELECT MAX(userId) FROM `t_userinfo`;");
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
