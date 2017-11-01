package com.yangqugame.db.dao.data;

import com.yangqugame.comm.db.PUQueryTerms;
import com.yangqugame.comm.db.PUSmartBeanDAO;
import com.yangqugame.db.DBManager;
import com.yangqugame.db.entry.data.TFootballerdata;

import java.util.List;

/**
 * Created by phiau on 2017/10/31 0031.
 */
public class TFootballerdataDao extends PUSmartBeanDAO<TFootballerdata> {

    public TFootballerdataDao() {
        super("", "t_footballerData", DBManager.getUserPool());
    }

    public int insert(TFootballerdata o) {
        return super.insert(o, false);
    }

    public List<TFootballerdata> queryList(long ownerId) {
        PUQueryTerms qt = new PUQueryTerms();
        qt.where("ownerId", ownerId);
        return queryList(qt);
    }
}
