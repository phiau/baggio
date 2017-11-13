package com.yangqugame.db.entry.data;


public class TLineup {

  public long ownerid;
  public int lineupid;
  public int ace;
  public int []positions = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

  public long getOwnerid() {
    return ownerid;
  }

  public void setOwnerid(long ownerid) {
    this.ownerid = ownerid;
  }

  public int getLineupid() {
    return lineupid;
  }

  public void setLineupid(int lineupid) {
    this.lineupid = lineupid;
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
