package com.yangqugame.db.entry.data;

import com.yangqugame.message.annotation.Proto;
import x1.proto.pb.BeanContainer;

@Proto(message = BeanContainer.TMail.class)
public class TMail {

  public short id;
  public long userid;
  public long sender;
  public String titile;
  public byte status;
  public String items;
  public java.sql.Timestamp time;


  public short getId() {
    return id;
  }

  public void setId(short id) {
    this.id = id;
  }


  public long getUserid() {
    return userid;
  }

  public void setUserid(long userid) {
    this.userid = userid;
  }


  public long getSender() {
    return sender;
  }

  public void setSender(long sender) {
    this.sender = sender;
  }


  public String getTitile() {
    return titile;
  }

  public void setTitile(String titile) {
    this.titile = titile;
  }


  public byte getStatus() {
    return status;
  }

  public void setStatus(byte status) {
    this.status = status;
  }


  public String getItems() {
    return items;
  }

  public void setItems(String items) {
    this.items = items;
  }


  public java.sql.Timestamp getTime() {
    return time;
  }

  public void setTime(java.sql.Timestamp time) {
    this.time = time;
  }

}
