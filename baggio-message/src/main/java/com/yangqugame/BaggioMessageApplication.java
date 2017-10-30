package com.yangqugame;

import com.yangqugame.annotation.ProtoManager;
import com.yangqugame.comm.util.PropertiesConfigUtil;
import com.yangqugame.db.DBManager;
import com.yangqugame.global.BaseConfig;
import com.yangqugame.global.ServerRuntime;
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
        PropertiesConfigUtil.file2ClassInstance("./instance/message/config/config.properties", BaseConfig.class);
        return true;
    }

    /** 第二步：初始化数据库链接 **/
    public static boolean initDb() {
        DBManager.init(BaseConfig.getUserDataConfigFile(), BaseConfig.getSystemDataConfigFile());
        return true;
    }

    /** 第三步：提前处理proto协议，建立映射关系 **/
    public static void initProtoRelation() {
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
