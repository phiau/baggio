package com.yangqugame.message.bean;

import com.yangqugame.db.entry.data.TMail;
import com.yangqugame.message.annotation.Proto;
import x1.proto.pb.BaseInfo;

import java.util.List;

/**
 * Created by phiau on 2017/11/13 0013.
 */
@Proto(code = 103103, message = BaseInfo.MailInfoMessage.class)
public class MailInfo {

    private List<TMail> mails;

    public List<TMail> getMails() {
        return mails;
    }

    public void setMails(List<TMail> mails) {
        this.mails = mails;
    }
}
