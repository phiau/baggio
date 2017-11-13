package com.yangqugame.db.entry.data;

import com.yangqugame.message.annotation.Proto;
import x1.proto.pb.BeanContainer;

@Proto(message = BeanContainer.UserInfo.class)
public class UserInfo {

  private long userid;
  private int accountid;
  private String nickname;
  private String face;
  private int exp;
  private int level;
  private int vip;
  private int diamond;
  private int gold;
  private int honor;
  private int st;
  private int bp;
  private int commonfragment;
  private int unionid;
  private int unionposition;
  private int chiefcoach;
  private int asscoach;
  private int chiefscout;
  private int assscout;
  private int chiefsponsor;
  private int asssponsor;

  public synchronized void addExp(int num) { this.exp += num; }
  public synchronized void addDiamond(int num) { this.diamond += num; }
  public synchronized void addGold(int num) { this.gold += num; }
  public synchronized void addHonor(int num) { this.honor += num; }
  public synchronized void addST(int num) { this.st += num; }
  public synchronized void addBP(int num) { this.bp += num; }

  public long getUserid() {
    return userid;
  }

  public void setUserid(long userid) {
    this.userid = userid;
  }


  public int getAccountid() {
    return accountid;
  }

  public void setAccountid(int accountid) {
    this.accountid = accountid;
  }


  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }


  public String getFace() {
    return face;
  }

  public void setFace(String face) {
    this.face = face;
  }


  public int getExp() {
    return exp;
  }

  public void setExp(int exp) {
    this.exp = exp;
  }


  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }


  public int getVip() {
    return vip;
  }

  public void setVip(int vip) {
    this.vip = vip;
  }


  public int getDiamond() {
    return diamond;
  }

  public void setDiamond(int diamond) {
    this.diamond = diamond;
  }


  public int getGold() {
    return gold;
  }

  public void setGold(int gold) {
    this.gold = gold;
  }


  public int getSt() {
    return st;
  }

  public void setSt(int st) {
    this.st = st;
  }


  public int getBp() {
    return bp;
  }

  public void setBp(int bp) {
    this.bp = bp;
  }


  public int getCommonfragment() {
    return commonfragment;
  }

  public void setCommonfragment(int commonfragment) {
    this.commonfragment = commonfragment;
  }


  public int getUnionid() {
    return unionid;
  }

  public void setUnionid(int unionid) {
    this.unionid = unionid;
  }


  public int getUnionposition() {
    return unionposition;
  }

  public void setUnionposition(int unionposition) {
    this.unionposition = unionposition;
  }


  public int getChiefcoach() {
    return chiefcoach;
  }

  public void setChiefcoach(int chiefcoach) {
    this.chiefcoach = chiefcoach;
  }


  public int getAsscoach() {
    return asscoach;
  }

  public void setAsscoach(int asscoach) {
    this.asscoach = asscoach;
  }


  public int getChiefscout() {
    return chiefscout;
  }

  public void setChiefscout(int chiefscout) {
    this.chiefscout = chiefscout;
  }


  public int getAssscout() {
    return assscout;
  }

  public void setAssscout(int assscout) {
    this.assscout = assscout;
  }


  public int getChiefsponsor() {
    return chiefsponsor;
  }

  public void setChiefsponsor(int chiefsponsor) {
    this.chiefsponsor = chiefsponsor;
  }


  public int getAsssponsor() {
    return asssponsor;
  }

  public void setAsssponsor(int asssponsor) {
    this.asssponsor = asssponsor;
  }

}
