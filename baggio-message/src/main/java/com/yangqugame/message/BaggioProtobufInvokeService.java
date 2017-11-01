package com.yangqugame.message;

import com.yangqugame.message.annotation.ProtoManager;
import com.yangqugame.utils.StringUtils;
import jazmin.log.Logger;
import jazmin.log.LoggerFactory;
import jazmin.server.protobuf.Context;
import jazmin.server.protobuf.ProtobufInvokeService;
import jazmin.server.protobuf.ProtobufMessage;
import jazmin.util.JSONUtil;

import java.lang.reflect.Method;


/**
 * Created by ging on 27/08/2017.
 * baggio
 */

public class BaggioProtobufInvokeService implements ProtobufInvokeService {

    private static Logger logger = LoggerFactory.getLogger(BaggioProtobufInvokeService.class);

    @Override
    public void invokeService(Context context) {
        ProtobufMessage requestMessage = context.getRequestMessage();
        int code = requestMessage.getId();
        Object bean = ProtoManager.byte2Message(requestMessage.getData(), code);
        if (null != bean) {
            logger.debug("received message from client: {}", JSONUtil.toJson(bean));
        } else {
            logger.error("parse client message exception code:{}", code);
            return;
        }
        try {
            Class cls = ProtoManager.getHandlerClass(code);
            String m = ProtoManager.getHandlerMethod(code);
            if (null == cls || StringUtils.isNullOrEmpty(m)) {
                logger.error("can not find cls or method with code : {}", code);
                return;
            }
            Method method = cls.getMethod(m, Context.class, bean.getClass());
            method.invoke(cls.newInstance(), context, bean);
        } catch (Exception e) {
            logger.error("invoke method exception code:{}", code, e);
        }
    }
}
