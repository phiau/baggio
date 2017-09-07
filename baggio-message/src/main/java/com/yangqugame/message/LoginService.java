package com.yangqugame.message;

import com.google.protobuf.InvalidProtocolBufferException;
import com.yangqugame.model.JsonResult;
import com.yangqugame.model.VerifyResult;
import com.yangqugame.msgBean.ReqLogin;
import com.yangqugame.msgBean.ResLogin;
import com.yangqugame.msgUtils.MessageSender;
import jazmin.driver.http.HttpClientDriver;
import jazmin.driver.http.HttpResponse;
import jazmin.log.Logger;
import jazmin.log.LoggerFactory;
import jazmin.server.protobuf.Context;
import jazmin.server.protobuf.ProtobufMessage;
import jazmin.util.JSONUtil;
import x1.proto.pb.Login;

import java.io.IOException;


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
            Login.ResLoginMessage res = Login.ResLoginMessage.newBuilder().setUserId(1001).build();
            ProtobufMessage msg = new ProtobufMessage(10002, res.toByteArray().length, res.toByteArray());
            context.ret(msg);
        } catch (InvalidProtocolBufferException e) {
            logger.catching(e);
        }
    }

    private final static String verifyUrl = "http://127.0.0.1:8080/verify";

    @Override
    public void handler(final Context context, Object o) {
        ReqLogin reqLogin = (ReqLogin) o;

        HttpClientDriver hd = new HttpClientDriver();
        hd.get(verifyUrl).addQueryParam("accessToken", reqLogin.getAccessToken()).execute((HttpResponse rsp, Throwable e) -> {
            ResLogin resLogin = new ResLogin();
            try {
                String body = rsp.getResponseBody();
                JsonResult result = JSONUtil.fromJson(body, JsonResult.class);
                if (result.isSuccess()) {
                    VerifyResult verifyResult = JSONUtil.fromJson(result.getData().toString(), VerifyResult.class);
                    resLogin.setUserId(verifyResult.accountId);
                    logger.debug(String.format("verify success, account id is %d", verifyResult.accountId));
                } else {
                    logger.debug(String.format("verify failed"));
                }
            } catch (IOException e1) {
                e1.printStackTrace();
                logger.debug(String.format("verify failed"));
            }
            logger.debug(String.format("verify result to client : %s", JSONUtil.toJson(resLogin)));
            MessageSender.send(context, resLogin);
        });
    }
}
