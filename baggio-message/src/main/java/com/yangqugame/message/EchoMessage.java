package com.yangqugame.message;

import com.yangqugame.service.EchoService;
import jazmin.core.Jazmin;
import jazmin.driver.rpc.JazminRpcDriver;
import jazmin.server.msg.Context;

/**
 * Created by ging on 07/07/2017.
 * baggio
 */

public class EchoMessage {

    public void echo(Context context, String msg) {
        JazminRpcDriver rpcDriver = Jazmin.getDriver(JazminRpcDriver.class);
        EchoService service = rpcDriver.create(EchoService.class, "cluster");
        context.ret("echo:" + service.echo(msg));
    }

}
