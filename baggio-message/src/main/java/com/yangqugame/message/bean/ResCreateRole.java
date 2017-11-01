package com.yangqugame.message.bean;

import com.yangqugame.message.annotation.Proto;
import com.yangqugame.db.entry.data.TFootballerdata;
import x1.proto.pb.Login;

/**
 * Created by phiau on 2017/11/1 0001.
 */
@Proto(code = 101103, message = Login.ResCreateRoleMessage.class)
public class ResCreateRole {
    private boolean isSuccess;
    private TFootballerdata newRole;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public TFootballerdata getNewRole() {
        return newRole;
    }

    public void setNewRole(TFootballerdata newRole) {
        this.newRole = newRole;
    }
}
