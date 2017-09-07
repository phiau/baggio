package com.yangqugame;

import com.yangqugame.annotation.ProtoManager;
import com.yangqugame.message.BaggioProtobufInvokeService;
import jazmin.core.Jazmin;
import jazmin.core.app.Application;
import jazmin.server.msg.MessageServer;
import jazmin.server.protobuf.ProtobufServer;
import x1.proto.event.EventConst;

/**
 * Created by ging on 05/07/2017.
 * baggio
 */

public class BaggioMessageApplication extends Application {

    public void localInit() {
        ProtoManager.scan("com");
    }

    @Override
    public void init() throws Exception {
        super.init();
        localInit();
        ProtobufServer server = Jazmin.getServer(ProtobufServer.class);
        BaggioProtobufInvokeService invokeService = new BaggioProtobufInvokeService();
        server.setProtobufInvokeService(invokeService);
    }
}
