package com.yangqugame.message;

import com.google.protobuf.InvalidProtocolBufferException;
import jazmin.log.Logger;
import jazmin.log.LoggerFactory;
import jazmin.server.protobuf.Context;
import jazmin.server.protobuf.ProtobufMessage;
import x1.proto.pb.Login;

/**
 * Created by ging on 27/08/2017.
 * baggio
 */

public class LoginService implements MessageService {

    private static Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Override
    public void invoke(Context context) {
        ProtobufMessage requestMessage = context.getRequestMessage();
        byte[] data = requestMessage.getData();
        try {
            Login.ReqLoginMessage message = Login.ReqLoginMessage.parseFrom(data);
            logger.info(message);
            Login.ResLoginMessage res = Login.ResLoginMessage.newBuilder().addResult(1).build();
            ProtobufMessage msg = new ProtobufMessage(10002, res.toByteArray().length, res.toByteArray());
            context.ret(msg);
        } catch (InvalidProtocolBufferException e) {
            logger.catching(e);
        }
    }
}
