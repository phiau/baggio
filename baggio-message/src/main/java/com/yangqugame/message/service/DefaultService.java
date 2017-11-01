package com.yangqugame.message.service;

import jazmin.log.Logger;
import jazmin.log.LoggerFactory;
import jazmin.server.protobuf.Context;

/**
 * Created by Administrator on 2017/9/5 0005.
 */
public class DefaultService {

    private static Logger logger = LoggerFactory.getLogger(DefaultService.class);

    public void handler(Context context, Object o) {
        logger.error("can not found service");
    }
}
