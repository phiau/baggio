package com.yangqugame;

import com.yangqugame.message.BaggioProtobufInvokeService;
import jazmin.core.Jazmin;
import jazmin.core.app.Application;
import jazmin.server.msg.MessageServer;
import jazmin.server.protobuf.ProtobufServer;

/**
 * Created by ging on 05/07/2017.
 * baggio
 */

public class BaggioMessageApplication extends Application {

    @Override
    public void init() throws Exception {
        super.init();
        ProtobufServer server = Jazmin.getServer(ProtobufServer.class);
        BaggioProtobufInvokeService invokeService = new BaggioProtobufInvokeService();
        server.setProtobufInvokeService(invokeService);
    }
}
