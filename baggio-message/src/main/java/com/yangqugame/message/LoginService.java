package com.yangqugame.message;

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
import jazmin.util.JSONUtil;

import java.io.IOException;

/**
 * Created by ging on 27/08/2017.
 * baggio
 */

public class LoginService implements MessageService {

    private static Logger logger = LoggerFactory.getLogger(LoginService.class);
    private final static String verifyUrl = "http://127.0.0.1:8080/verify";

    @Override
    public void handler(Context context, Object o) {
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
