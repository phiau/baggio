package com.yangqugame.message.bean;

import com.yangqugame.message.annotation.Proto;
import com.yangqugame.db.entry.data.UserInfo;
import x1.proto.pb.Login;

import java.util.List;

/**
 * Created by Administrator on 2017/9/5 0005.
 */
@Proto(code = 101101, message = Login.ResLoginMessage.class)
public class ResLogin {

    private int userNum;

    private List<UserInfo> userList;

    public int getUserNum() {
        return userNum;
    }

    public void setUserNum(int userNum) {
        this.userNum = userNum;
    }

    public List<UserInfo> getUserList() {
        return userList;
    }

    public void setUserList(List<UserInfo> userList) {
        this.userList = userList;
    }
}
