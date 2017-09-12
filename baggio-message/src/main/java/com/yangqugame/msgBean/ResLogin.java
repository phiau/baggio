package com.yangqugame.msgBean;

import com.yangqugame.annotation.Proto;
import com.yangqugame.db.entry.data.RoleInfo;
import x1.proto.pb.Login;

import java.util.List;

/**
 * Created by Administrator on 2017/9/5 0005.
 */
@Proto(code = 10002, message = Login.ResLoginMessage.class)
public class ResLogin {

    private int roleNum;

    private List<RoleInfo> roleList;

    public int getRoleNum() {
        return roleNum;
    }

    public void setRoleNum(int roleNum) {
        this.roleNum = roleNum;
    }

    public List<RoleInfo> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleInfo> roleList) {
        this.roleList = roleList;
    }
}
