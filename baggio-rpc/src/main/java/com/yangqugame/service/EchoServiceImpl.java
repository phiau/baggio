package com.yangqugame.service;


/**
 * Created by ging on 04/07/2017.
 * baggio
 */

public class EchoServiceImpl implements EchoService {


    /**
     *
     * @param msg 消息
     * @return 大写消息
     */
    @Override
    public String echo(String msg) {
        return msg.toUpperCase();
    }

}
