package com.yangqugame.message;

import com.yangqugame.global.BaseConfig;
import com.yangqugame.model.JsonResult;
import com.yangqugame.model.VerifyResult;
import com.yangqugame.msgBean.ReqLogin;
import com.yangqugame.msgBean.ResLogin;
import com.yangqugame.msgUtils.MessageSender;
import com.yangqugame.user.Role;
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
public class LoginService {

    private static Logger logger = LoggerFactory.getLogger(LoginService.class);

    public void login(Context context, ReqLogin reqLogin) {
        HttpClientDriver hd = new HttpClientDriver();
        String verifyUrl = BaseConfig.getVerifyServerUrl();
        hd.get(verifyUrl).addQueryParam("accessToken", reqLogin.getAccessToken()).execute((HttpResponse rsp, Throwable e) -> {
            try {
                String body = rsp.getResponseBody();
                JsonResult result = JSONUtil.fromJson(body, JsonResult.class);
                if (result.isSuccess()) {
                    VerifyResult verifyResult = JSONUtil.fromJson(result.getData().toString(), VerifyResult.class);
                    Role.verifyAction(context, verifyResult.accountId);
                    logger.debug(String.format("verify success, account id is %d", verifyResult.accountId));
                } else {
                    Role.verifyAction(context, -1);
                    logger.debug(String.format("verify failed"));
                }
            } catch (IOException e1) {
                e1.printStackTrace();
                logger.debug(String.format("verify failed"));
            }
        });
    }
}
