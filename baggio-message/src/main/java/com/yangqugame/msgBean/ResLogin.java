package com.yangqugame.msgBean;

import com.yangqugame.annotation.Proto;
import x1.proto.pb.Login;

/**
 * Created by Administrator on 2017/9/5 0005.
 */
@Proto(code = 10002, message = Login.ResLoginMessage.class)
public class ResLogin {

    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
