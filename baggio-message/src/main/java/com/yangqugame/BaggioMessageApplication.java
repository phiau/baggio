package com.yangqugame;

import com.yangqugame.annotation.ProtoManager;
import com.yangqugame.db.DBPoolMgr;
import com.yangqugame.global.BaseConfig;
import com.yangqugame.global.ConfigManager;
import com.yangqugame.global.DbConfig;
import com.yangqugame.message.BaggioProtobufInvokeService;
import jazmin.core.Jazmin;
import jazmin.core.app.Application;
import jazmin.log.Logger;
import jazmin.log.LoggerFactory;
import jazmin.server.protobuf.ProtobufServer;

/**
 * Created by ging on 05/07/2017.
 * baggio
 */

public class BaggioMessageApplication extends Application {

    final transient static Logger logger = LoggerFactory.getLogger(BaggioMessageApplication.class);

    /** 第一步：加载配置 **/
    public static boolean loadConfig() {
        if (ConfigManager.loadBaseConfig("./instance/message/config/config.properties")) {
            if (ConfigManager.loadDbConfig(BaseConfig.getDbConfigFile())) {
                return true;
            }
        }
        return false;
    }

    /** 第二步：初始化数据库链接 **/
    public static boolean initDb() {
        return DBPoolMgr.init(DbConfig.getAddress(), DbConfig.getPort(), DbConfig.getUser(), DbConfig.getPsw(), DbConfig.getDataDbName(), DbConfig.getConfDbName(), DbConfig.getCharSet());
    }

    /** 第三步：提前处理proto协议，建立映射关系 **/
    public void initProtoRelation() {
        ProtoManager.scan("com");
    }

    /** 最后：打印配置等信息 **/
    public static void baggioInfo() {
        String msg = String.format("\n%s\n\n%s\n\n%s\n", BaseConfig.info(), BaseConfig.info(), ProtoManager.info());
        logger.info(msg);
    }

    @Override
    public void init() throws Exception {
        super.init();
        loadConfig();
        initDb();
        initProtoRelation();
        baggioInfo();

        ProtobufServer server = Jazmin.getServer(ProtobufServer.class);
        BaggioProtobufInvokeService invokeService = new BaggioProtobufInvokeService();
        server.setProtobufInvokeService(invokeService);
    }
}
