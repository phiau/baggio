package com.yangqugame.db.dao.data;

import com.yangqugame.comm.db.PUQueryTerms;
import com.yangqugame.comm.db.PUSmartBeanDAO;
import com.yangqugame.db.DBManager;
import com.yangqugame.db.entry.data.TBackpackItem;

import java.util.List;


/**
 * Created by phiau on 2017/11/2 0002.
 */
public class TBackpackDao extends PUSmartBeanDAO<TBackpackItem> {

    public TBackpackDao() {
        super("", "t_backpackItem", DBManager.getUserPool());
    }

    public int delete(TBackpackItem bean) {
        PUQueryTerms qt = new PUQueryTerms();
        qt.where("userId", bean.getUserId());
        qt.where("itemId", bean.getItemId());
        qt.where("itemNum", bean.getItemNum());
        qt.limit(1);
        return super.delete(qt);
    }

    public void insert(TBackpackItem bean) {
        super.insert(bean, false);
    }

    public int update(TBackpackItem bean, int sourceNum) {
        PUQueryTerms qt = new PUQueryTerms();
        qt.where("userId", bean.getUserId());
        qt.where("itemId", bean.getItemId());
        qt.where("itemNum", sourceNum);
        qt.limit(1);
        return super.update(bean, qt);
    }

    public List<TBackpackItem> queryList(long userId) {
        PUQueryTerms qt = new PUQueryTerms();
        qt.where("userId", userId);
        return super.queryList(qt);
    }
}
