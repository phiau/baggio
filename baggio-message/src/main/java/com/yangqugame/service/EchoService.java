package com.yangqugame.service;

import jazmin.server.msg.Context;
import jazmin.server.msg.Service;

/**
 * Created by ging on 07/07/2017.
 * baggio
 */

public class EchoService {

    public void echo(Context context, String msg) {
        context.ret("echo:" + msg);
    }

}
