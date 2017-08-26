package com.yangqugame.message;

import jazmin.log.Logger;
import jazmin.log.LoggerFactory;
import jazmin.server.protobuf.Context;
import jazmin.server.protobuf.ProtobufInvokeService;
import jazmin.server.protobuf.ProtobufMessage;

/**
 * Created by ging on 27/08/2017.
 * baggio
 */

public class BaggioProtobufInvokeService implements ProtobufInvokeService {

    private static Logger logger = LoggerFactory.getLogger(BaggioProtobufInvokeService.class);

    @Override
    public void invokeService(Context context) {
        ProtobufMessage requestMessage = context.getRequestMessage();
        logger.info(String.format("invoke service: id %d length %d data %s", requestMessage.getId(), requestMessage.getLength(), new String(requestMessage.getData())));
        String message = "baggio-message";
        ProtobufMessage msg = new ProtobufMessage(10002, message.getBytes().length, message.getBytes());
        context.ret(msg);
    }
}
