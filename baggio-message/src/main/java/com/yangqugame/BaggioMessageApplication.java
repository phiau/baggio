package com.yangqugame;

import com.yangqugame.service.EchoService;
import jazmin.core.Jazmin;
import jazmin.core.app.Application;
import jazmin.server.msg.MessageServer;

/**
 * Created by ging on 05/07/2017.
 * baggio
 */

public class BaggioMessageApplication extends Application {

    @Override
    public void init() throws Exception {
        super.init();
        MessageServer server = Jazmin.getServer(MessageServer.class);
        server.registerService(new EchoService());
    }
}
