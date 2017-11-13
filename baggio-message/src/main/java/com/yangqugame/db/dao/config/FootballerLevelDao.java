package com.yangqugame.db.dao.config;

import com.yangqugame.comm.db.PUQueryTerms;
import com.yangqugame.comm.db.PUSmartBeanDAO;
import com.yangqugame.db.DBManager;
import com.yangqugame.db.entry.config.FootballerLevel;

import java.util.List;

/**
 * Created by phiau on 2017/11/6 0006.
 */
public class FootballerLevelDao extends PUSmartBeanDAO<FootballerLevel> {

    public FootballerLevelDao() {
        super("", "footballerlevel", DBManager.getSystemPool());
    }

    public List<FootballerLevel> queryList() {
        PUQueryTerms qt = new PUQueryTerms();
        return super.queryList(qt);
    }
}
