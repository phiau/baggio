package com.yangqugame.msgBean;

/**
 * Created by Administrator on 2017/8/28 0028.
 */
public class ReqLogin {
    private String accountName;
    private String password;
    private int platform;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    @Override
    public String toString() {
        return "ReqLogin{" +
                "accountName='" + accountName + '\'' +
                ", password='" + password + '\'' +
                ", platform=" + platform +
                '}';
    }
}
