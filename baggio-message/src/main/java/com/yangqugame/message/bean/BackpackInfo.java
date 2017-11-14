package com.yangqugame.message.bean;

import com.yangqugame.db.entry.data.TBackpackItem;
import com.yangqugame.message.annotation.Proto;
import x1.proto.pb.BaseInfo;

import java.util.List;

/**
 * Created by phiau on 2017/11/13 0013.
 */
@Proto(code = 103102, message = BaseInfo.BackpackInfoMessage.class)
public class BackpackInfo {

    private int num=0;
    private List<TBackpackItem> items;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<TBackpackItem> getItems() {
        return items;
    }

    public void setItems(List<TBackpackItem> items) {
        this.items = items;
    }
}
