package com.yangqugame.comm.config;

/**
 * Created by phiau on 2017/10/27 0027.
 */
public class CacheConfig {
    private String host;
    private String psw;
    private int port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String info() {
        return "CacheConfig{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}
