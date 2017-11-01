package com.yangqugame.message;

import com.google.protobuf.Message;
import com.yangqugame.message.annotation.ProtoManager;
import com.yangqugame.user.UserManager;
import jazmin.log.Logger;
import jazmin.log.LoggerFactory;
import jazmin.server.protobuf.Context;
import jazmin.server.protobuf.ProtobufMessage;



/**
 * Created by Administrator on 2017/9/6 0006.
 */
public class MessageSender {

    private static Logger logger = LoggerFactory.getLogger(MessageSender.class);

    public static void send(Context context, Object o) {
        ProtobufMessage protobufMessage = bean2ProtobufMessage(o);
        if (null != protobufMessage) {
            context.ret(protobufMessage);
            context.close(false);
        }
    }

    public static void sendByUserId(Long userId, Object o) {
        int accountId = UserManager.getAccountIdByUserId(userId);
        if (0 < accountId) {
            sendByAccountId(accountId, o);
        }
    }

    public static void sendByAccountId(int accountId, Object o) {
        ProtobufMessage protobufMessage = bean2ProtobufMessage(o);
        if (null != protobufMessage) {
            SessionManager.sendMessage(accountId, protobufMessage);
        }
    }

    public static ProtobufMessage bean2ProtobufMessage(Object o) {
        if (ProtoManager.existBeanProto(o.getClass())) {
            Message message = ProtoManager.bean2Message(o);
            int code = ProtoManager.getCodeByBean(o.getClass());
            byte[] data = message.toByteArray();
            return new ProtobufMessage(code, data.length, data);
        } else {
            logger.error(String.format("can not found ths class:%s Corresponding the proto", o.getClass().getName()));
            return null;
        }
    }
}
