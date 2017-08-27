package com.yangqugame.message;

import jazmin.log.Logger;
import jazmin.log.LoggerFactory;
import jazmin.server.protobuf.Context;
import jazmin.server.protobuf.ProtobufInvokeService;
import jazmin.server.protobuf.ProtobufMessage;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by ging on 27/08/2017.
 * baggio
 */

public class BaggioProtobufInvokeService implements ProtobufInvokeService {

    private static Logger logger = LoggerFactory.getLogger(BaggioProtobufInvokeService.class);

    private static Map<Integer, String>  services = new HashMap<>();

    // 注册protobuf 服务
    static {
        services.put(10001, "com.yangqugame.message.LoginService");
    }

    @Override
    public void invokeService(Context context) {
        ProtobufMessage requestMessage = context.getRequestMessage();
        int id = requestMessage.getId();
        String className = services.get(id);
        try {
            MessageService service = (MessageService) Class.forName(className).newInstance();
            service.invoke(context);
        } catch (Exception e) {
            logger.catching(e);
        }

    }
}
