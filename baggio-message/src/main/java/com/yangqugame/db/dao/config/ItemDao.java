package com.yangqugame.db.dao.config;

import com.yangqugame.comm.db.PUQueryTerms;
import com.yangqugame.comm.db.PUSmartBeanDAO;
import com.yangqugame.db.DBManager;
import com.yangqugame.db.entry.config.Item;

import java.util.List;

/**
 * Created by phiau on 2017/11/2 0002.
 */
public class ItemDao extends PUSmartBeanDAO<Item> {

    public ItemDao() {
        super("", "item", DBManager.getSystemPool());
    }

    public List<Item> queryList() {
        PUQueryTerms qt = new PUQueryTerms();
        return super.queryList(qt);
    }
}
