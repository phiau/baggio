package com.yangqugame.service;

import jazmin.driver.rpc.AsyncCallback;

/**
 * Created by ging on 22/08/2017.
 * baggio
 */

public interface EchoServiceAsync {

    void echo(String msg, AsyncCallback cb) throws Exception;

}
