package com.yangqugame.db.dao.data;

import com.yangqugame.comm.db.PUQueryTerms;
import com.yangqugame.comm.db.PUSmartBeanDAO;
import com.yangqugame.db.DBManager;
import com.yangqugame.db.entry.data.TLineup;

import java.sql.Connection;
import java.util.List;

/**
 * Created by phiau on 2017/11/1 0001.
 */
public class TLineupDao extends PUSmartBeanDAO<TLineup> {

    public TLineupDao() {
        super("", "t_lineup", DBManager.getUserPool());
    }

    public List<TLineup> queryList(long ownerId) {
        PUQueryTerms qt = new PUQueryTerms();
        qt.where("ownerId", ownerId);
        return super.queryList(qt);
    }
}
