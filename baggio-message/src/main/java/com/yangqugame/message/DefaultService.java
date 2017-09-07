package com.yangqugame.message;

import jazmin.log.Logger;
import jazmin.log.LoggerFactory;
import jazmin.server.protobuf.Context;

/**
 * Created by Administrator on 2017/9/5 0005.
 */
public class DefaultService implements MessageService {

    private static Logger logger = LoggerFactory.getLogger(DefaultService.class);

    @Override
    public void handler(Context context, Object o) {
        logger.error("找不到请求的 service");
    }
}
