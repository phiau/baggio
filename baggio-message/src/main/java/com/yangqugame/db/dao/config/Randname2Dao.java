package com.yangqugame.db.dao.config;

import com.yangqugame.comm.db.PUQueryTerms;
import com.yangqugame.comm.db.PUSmartBeanDAO;
import com.yangqugame.db.DBManager;
import com.yangqugame.db.entry.config.Randname2;

import java.util.List;

/**
 * Created by phiau on 2017/9/12 0012.
 */
public class Randname2Dao extends PUSmartBeanDAO<Randname2> {

    public Randname2Dao() {
        super("", "randname2", DBManager.getSystemPool());
    }

    @Override
    protected List<Randname2> queryList(PUQueryTerms qt, String... excludeProperties) {
        return super.queryList(qt, excludeProperties);
    }

    public List<Randname2> queryList() {
        PUQueryTerms qt = new PUQueryTerms();
        return queryList(qt);
    }
}
