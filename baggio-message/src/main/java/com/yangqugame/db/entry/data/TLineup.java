package com.yangqugame.db.entry.data;

import com.yangqugame.message.annotation.Proto;
import x1.proto.pb.BeanContainer;

@Proto(message = BeanContainer.TLineup.class)
public class TLineup {

  public long ownerid;
  public int lineupId;
  public int ace;
  public int [] positions = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

  public long getOwnerid() {
    return ownerid;
  }

  public void setOwnerid(long ownerid) {
    this.ownerid = ownerid;
  }

  public int getLineupId() {
    return lineupId;
  }

  public void setLineupId(int lineupId) {
    this.lineupId = lineupId;
  }

  public int getAce() {
    return ace;
  }

  public void setAce(int ace) {
    this.ace = ace;
  }

  public int getPositions(int pos) {
    return positions[pos];
  }

  public void setPositions(int pos, int value) {
    this.positions[pos] = value;
  }
}
