package com.yangqugame.global;

/**
 * 服务器一些静态配置
 * Created by Administrator on 2017/9/6 0006.
 */
public class ServerConfig {

    private static short serverId = 1;

    public static void setServerId(short serverId) {
        ServerConfig.serverId = serverId;
    }

    public static short getServerId() {
        return serverId;
    }
}
