package com.yangqugame.db.entry.data;

import com.yangqugame.message.annotation.Proto;
import x1.proto.pb.BeanContainer;

import java.util.List;

@Proto(message = BeanContainer.FootballerData.class)
public class TFootballerdata {

  public long ownerId;
  public int roleType;
  public int awakenState;
  public int level;
  public int exp;
  public int star;
  public int quality;
  public double power;
  public double technic;
  public double vitality;
  public double speed;
  public int active;
  public int fragment;
  public int mainSkillLev;
  public int []skillLevel = {0, 0, 0, 0};
  public int combat;

  public synchronized void addFragment(int num) {
    this.fragment += num;
  }


  public long getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(long ownerId) {
    this.ownerId = ownerId;
  }


  public int getRoleType() {
    return roleType;
  }

  public void setRoleType(int roleType) {
    this.roleType = roleType;
  }


  public int getAwakenState() {
    return awakenState;
  }

  public void setAwakenState(int awakenState) {
    this.awakenState = awakenState;
  }


  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }


  public int getExp() {
    return exp;
  }

  public void setExp(int exp) {
    this.exp = exp;
  }


  public int getStar() {
    return star;
  }

  public void setStar(int star) {
    this.star = star;
  }


  public int getQuality() {
    return quality;
  }

  public void setQuality(int quality) {
    this.quality = quality;
  }


  public double getPower() {
    return power;
  }

  public void setPower(double power) {
    this.power = power;
  }


  public double getTechnic() {
    return technic;
  }

  public void setTechnic(double technic) {
    this.technic = technic;
  }


  public double getVitality() {
    return vitality;
  }

  public void setVitality(double vitality) {
    this.vitality = vitality;
  }


  public double getSpeed() {
    return speed;
  }

  public void setSpeed(double speed) {
    this.speed = speed;
  }


  public int getActive() {
    return active;
  }

  public void setActive(int active) {
    this.active = active;
  }


  public int getFragment() {
    return fragment;
  }

  public void setFragment(int fragment) {
    this.fragment = fragment;
  }


  public int getMainSkillLev() {
    return mainSkillLev;
  }

  public void setMainSkillLev(int mainSkillLev) {
    this.mainSkillLev = mainSkillLev;
  }

  public int[] getSkillLevel() {
    return skillLevel;
  }

  public void setSkillLevel(int[] skillLevel) {
    this.skillLevel = skillLevel;
  }

  public int getCombat() {
    return combat;
  }

  public void setCombat(int combat) {
    this.combat = combat;
  }

}
