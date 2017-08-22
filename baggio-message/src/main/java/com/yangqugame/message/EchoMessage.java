package com.yangqugame.message;

import jazmin.core.Jazmin;
import jazmin.driver.rpc.JazminRpcDriver;
import jazmin.server.msg.Context;
import jazmin.server.msg.Service;

/**
 * Created by ging on 07/07/2017.
 * baggio
 */

public class EchoMessage {

    public void echo(Context context, String msg) {
        context.ret("echo:" + msg);
    }

}
