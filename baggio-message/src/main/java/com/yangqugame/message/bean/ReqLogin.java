package com.yangqugame.message.bean;

import com.yangqugame.message.annotation.Proto;
import com.yangqugame.message.service.LoginService;
import x1.proto.pb.Login;

/**
 * Created by Administrator on 2017/8/28 0028.
 */
@Proto(code = 101100, message = Login.ReqLoginMessage.class, service = LoginService.class, method = "login")
public class ReqLogin {
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
