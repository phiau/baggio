package com.yangqugame.service;


public class Account {

  public int accountid;
  public byte accounttype;
  public String accountname;
  public String password;

  public int getAccountid() {
    return accountid;
  }

  public void setAccountid(int accountid) {
    this.accountid = accountid;
  }


  public byte getAccounttype() {
    return accounttype;
  }

  public void setAccounttype(byte accounttype) {
    this.accounttype = accounttype;
  }


  public String getAccountname() {
    return accountname;
  }

  public void setAccountname(String accountname) {
    this.accountname = accountname;
  }


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
