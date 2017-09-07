package com.yangqugame.msgBean;

import com.yangqugame.annotation.Proto;
import com.yangqugame.message.LoginService;
import x1.proto.pb.Login;

/**
 * Created by Administrator on 2017/8/28 0028.
 */
@Proto(code = 10001, message = Login.ReqLoginMessage.class, service = LoginService.class)
public class ReqLogin {
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
