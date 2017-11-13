package com.yangqugame.db.dao.config;

import com.yangqugame.comm.db.PUQueryTerms;
import com.yangqugame.comm.db.PUSmartBeanDAO;
import com.yangqugame.db.DBManager;
import com.yangqugame.db.entry.config.FootballerStar;

import java.util.List;


/**
 * Created by phiau on 2017/11/7 0007.
 */
public class FootballerStarDao extends PUSmartBeanDAO<FootballerStar> {

    public FootballerStarDao() {
        super("", "footballerstar", DBManager.getSystemPool());
    }

    public List<FootballerStar> queryList() {
        return super.queryList(new PUQueryTerms());
    }
}
