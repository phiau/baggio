package com.yangqugame.user;

import com.yangqugame.db.dao.data.TBackpackDao;
import com.yangqugame.db.entry.config.Item;
import com.yangqugame.db.entry.data.TBackpackItem;
import com.yangqugame.global.TableConfigs;

import java.util.List;

/**
 * 玩家背包
 * Created by phiau on 2017/11/2 0002.
 */
public class UserBackpack {

    private long userId;
    private List<TBackpackItem> items;

    public UserBackpack(long userId) {
        this.userId = userId;
        items = new TBackpackDao().queryList(userId);
    }

    protected synchronized void userItemIn(int itemId, int num) {
        if (0 < num) {
            int minIndex = -1;
            int min = Integer.MAX_VALUE;
            int i = 0;
            for (; i< items.size(); i++) {
                TBackpackItem item = items.get(i);
                if (item.getItemId() == itemId) {
                    if (min > item.getItemNum()) {
                        min = item.getItemNum();
                        minIndex = i;
                    }
                }
            }
            if (-1 != minIndex) {
                TBackpackItem item = items.get(minIndex);
                if (min > num) {
                    item.setItemNum(item.getItemNum() - num);
                    update2Db(item, item.getItemNum() + num);
                } else {
                    num -= min;
                    delete2DB(item);
                    items.remove(minIndex);
                    userItemIn(itemId, num);
                }
            }
        }
    }

    // 使用物品
    public synchronized void useItem(int itemId, int num) {
        if (TableConfigs.existItem(itemId) && 0 < num) {
            int total = 0;
            for (int i = 0; i< items.size(); i++) {
                TBackpackItem item = items.get(i);
                if (item.getItemId() == itemId) {
                    total += item.getItemNum();
                }
            }
            if (total > num) {
                userItemIn(itemId, num);
            }
        }
    }

    // 添加物品
    public synchronized void addItem(int itemId, int num) {
        if (TableConfigs.existItem(itemId) && 0 < num) {
            Item confItem = TableConfigs.getItem(itemId);
            int i = 0;
            for (; i< items.size(); i++) {
                TBackpackItem item = items.get(i);
                if (item.getItemId() == itemId) {
                    // 数量
                    if (confItem.amountlimit > item.getItemNum()) {
                        int tmp = confItem.amountlimit - item.getItemNum();
                        if (tmp >= num) {
                            item.setItemNum(item.getItemNum() + num);
                            update2Db(item, item.getItemNum() - num);
                        } else {
                            item.setItemNum(confItem.amountlimit);
                            update2Db(item, item.getItemNum() - tmp);
                            addItem(itemId, num - tmp);
                        }
                        break;
                    }
                }
            }
            if (i >= items.size()) {
                TBackpackItem newBackpack = new TBackpackItem();
                newBackpack.setItemNum(num);
                newBackpack.setItemId(itemId);
                newBackpack.setUserId(userId);
                items.add(newBackpack);
                insert2Db(newBackpack);
            }
        }
    }

    // 更新数据库
    protected void update2Db(TBackpackItem tBackpackItem, int sourceNum) {
        new TBackpackDao().update(tBackpackItem, sourceNum);
    }

    protected void insert2Db(TBackpackItem tBackpackItem) {
        new TBackpackDao().insert(tBackpackItem);
    }

    protected void delete2DB(TBackpackItem tBackpackItem) {
        new TBackpackDao().delete(tBackpackItem);
    }
}
