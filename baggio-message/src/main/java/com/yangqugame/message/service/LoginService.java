package com.yangqugame.message.service;

import com.yangqugame.global.BaseConfig;
import com.yangqugame.message.SessionManager;
import com.yangqugame.message.bean.ReqCreateRole;
import com.yangqugame.message.bean.ReqLogin;
import com.yangqugame.model.JsonResult;
import com.yangqugame.model.VerifyResult;
import com.yangqugame.user.OnlineUserActorManager;
import com.yangqugame.user.User;
import com.yangqugame.user.UserManager;
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

    // 玩家登录
    public void login(Context context, ReqLogin reqLogin) {
        HttpClientDriver hd = new HttpClientDriver();
        String verifyUrl = BaseConfig.getVerifyServerUrl();
        hd.get(verifyUrl).addQueryParam("accessToken", reqLogin.getAccessToken()).execute((HttpResponse rsp, Throwable e) -> {
            try {
                String body = rsp.getResponseBody();
                JsonResult result = JSONUtil.fromJson(body, JsonResult.class);
                if (result.isSuccess()) {
                    VerifyResult verifyResult = JSONUtil.fromJson(result.getData().toString(), VerifyResult.class);
                    User.verifyAction(context, verifyResult.accountId);
                    logger.debug(String.format("verify success, account id is %d", verifyResult.accountId));
                } else {
                    User.verifyAction(context, -1);
                    logger.debug(String.format("verify failed"));
                }
            } catch (IOException e1) {
                e1.printStackTrace();
                logger.debug(String.format("verify failed"));
            }
        });
    }

    // 创建玩家第一个球员
    public void createRole(Context context, ReqCreateRole createRole) {
        int accountId = SessionManager.getAccountBySession(context.getSession());
        long userId = UserManager.getUserIdByAccountId(accountId);
        OnlineUserActorManager.tellActorByUserId(userId, createRole);
    }
}
