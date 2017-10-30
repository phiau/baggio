package com.yangqugame.comm.config;

/**
 *  连接数据库配置
 * Created by phiau on 2017/10/27 0027.
 */
public class DbConfig {
    private String host;
    private String port;
    private String user;
    private String psw;
    private String datebase;
    private String charSet;
    private int maxConn = 10;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getDatebase() {
        return datebase;
    }

    public void setDatebase(String datebase) {
        this.datebase = datebase;
    }

    public String getCharSet() {
        return charSet;
    }

    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }

    public int getMaxConn() {
        return maxConn;
    }

    public void setMaxConn(int maxConn) {
        this.maxConn = maxConn;
    }

    public String info() {
        return "this{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", user='" + user + '\'' +
                ", datebase='" + datebase + '\'' +
                ", charSet='" + charSet + '\'' +
                ", maxConn='" + maxConn + '\'' +
                '}';
    }
}
