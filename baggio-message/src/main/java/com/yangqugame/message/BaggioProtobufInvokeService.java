package com.yangqugame.message;

import com.yangqugame.annotation.ProtoManager;
import jazmin.log.Logger;
import jazmin.log.LoggerFactory;
import jazmin.server.protobuf.Context;
import jazmin.server.protobuf.ProtobufInvokeService;
import jazmin.server.protobuf.ProtobufMessage;
import jazmin.util.JSONUtil;


/**
 * Created by ging on 27/08/2017.
 * baggio
 */

public class BaggioProtobufInvokeService implements ProtobufInvokeService {

    private static Logger logger = LoggerFactory.getLogger(BaggioProtobufInvokeService.class);

    @Override
    public void invokeService(Context context) {
        ProtobufMessage requestMessage = context.getRequestMessage();
        int id = requestMessage.getId();
        Class cls = ProtoManager.getHandlerClass(id);
        Object bean = ProtoManager.byte2Message(requestMessage.getData(), id);
        if (null != bean) {
            logger.debug("从客户端接收到的消息为:" + JSONUtil.toJson(bean));
        } else {
            logger.error("解析客户端消息异常 id:{}", id);
        }
        try {
            MessageService service = (MessageService) cls.newInstance();
            service.handler(context, bean);
        } catch (Exception e) {
            logger.catching(e);
        }
    }
}
