package com.yangqugame.service;

import jazmin.driver.rpc.AsyncCallback;

/**
 * Created by ging on 22/08/2017.
 * baggio
 */

public class EchoServiceAsyncImpl implements EchoServiceAsync {

    @Override
    public void echo(String msg, AsyncCallback cb) throws Exception {
        String result = msg.toUpperCase();
        cb.callback(result, null);
    }

}
