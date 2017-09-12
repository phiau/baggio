package com.yangqugame.db.entry.data;

import com.yangqugame.annotation.Proto;
import x1.proto.pb.Login;

@Proto(message = Login.RoleInfo.class)
public class RoleInfo {

  public long roleid;
  public int accountid;
  public String nickname;
  public String face;
  public int exp;
  public int level;
  public int vip;
  public int diamond;
  public int gold;
  public int st;
  public int bp;
  public int commonfragment;
  public int unionid;
  public int unionposition;
  public int chiefcoach;
  public int asscoach;
  public int chiefscout;
  public int assscout;
  public int chiefsponsor;
  public int asssponsor;


  public long getRoleid() {
    return roleid;
  }

  public void setRoleid(long roleid) {
    this.roleid = roleid;
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
