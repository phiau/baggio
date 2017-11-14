package com.yangqugame.db.entry.data;

import com.yangqugame.message.annotation.Proto;
import x1.proto.pb.BeanContainer;

@Proto(message = BeanContainer.BackpackItem.class)
public class TBackpackItem {

  public long userId;
  public int itemId;
  public int itemNum;


  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }


  public int getItemId() {
    return itemId;
  }

  public void setItemId(int itemId) {
    this.itemId = itemId;
  }


  public int getItemNum() {
    return itemNum;
  }

  public void setItemNum(int itemNum) {
    this.itemNum = itemNum;
  }

}
