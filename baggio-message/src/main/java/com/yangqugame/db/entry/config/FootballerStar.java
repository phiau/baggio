package com.yangqugame.db.entry.config;


public class FootballerStar {

  public int bornstar;       // 唯一性，对应roleInfo的bornStar
  public int bornneed;       // 激活所需的碎片数量
  public int echoproduce;    // 当获得重复球员时转换为碎片的数量
  public int commonneed;     // 兑换一个球员碎片所需的通用碎片数量
  public int fullcommonturn; // 当球员升至7星后转换为通用碎片的数量

  public int getBornstar() {
    return bornstar;
  }

  public void setBornstar(int bornstar) {
    this.bornstar = bornstar;
  }

  public int getBornneed() {
    return bornneed;
  }

  public void setBornneed(int bornneed) {
    this.bornneed = bornneed;
  }


  public int getEchoproduce() {
    return echoproduce;
  }

  public void setEchoproduce(int echoproduce) {
    this.echoproduce = echoproduce;
  }


  public int getCommonneed() {
    return commonneed;
  }

  public void setCommonneed(int commonneed) {
    this.commonneed = commonneed;
  }


  public int getFullcommonturn() {
    return fullcommonturn;
  }

  public void setFullcommonturn(int fullcommonturn) {
    this.fullcommonturn = fullcommonturn;
  }

}
