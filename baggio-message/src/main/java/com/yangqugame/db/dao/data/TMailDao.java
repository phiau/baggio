package com.yangqugame.db.dao.data;

import com.yangqugame.comm.db.PUQueryTerms;
import com.yangqugame.comm.db.PUSmartBeanDAO;
import com.yangqugame.db.DBManager;
import com.yangqugame.db.entry.data.TMail;

import java.util.List;

/**
 * Created by phiau on 2017/11/3 0003.
 */
public class TMailDao extends PUSmartBeanDAO<TMail> {

    public TMailDao() {
        super("", "t_mail", DBManager.getUserPool());
    }

    public int insert(TMail o) {
        return super.insert(o, true);
    }

    public List<TMail> queryList(long userId) {
        PUQueryTerms qt = new PUQueryTerms();
        qt.where("userId", userId);
        return super.queryList(qt);
    }
}
