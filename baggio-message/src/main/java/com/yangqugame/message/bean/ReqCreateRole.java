package com.yangqugame.message.bean;

import com.yangqugame.message.annotation.Proto;
import com.yangqugame.message.service.LoginService;
import x1.proto.pb.Login;

/**
 * Created by phiau on 2017/10/31 0031.
 */
@Proto(code = 101102, message = Login.ReqCreateRoleMessage.class, service = LoginService.class, method = "createRole")
public class ReqCreateRole {
    private int roleType;

    public int getRoleType() {
        return roleType;
    }

    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }
}
