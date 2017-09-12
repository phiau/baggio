package com.yangqugame.message;

import jazmin.server.msg.Session;
import jazmin.server.protobuf.Context;
import jazmin.server.protobuf.ProtobufMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by phiau on 2017/9/11 0011.
 */
public class SessionManager {

    private static Map<Integer, Session> accountId2sessionMap = new HashMap<>();    // key:account id, value:session
    private static Map<Session, Integer> session2AccountIdMap = new HashMap<>();    // key:session, value:account id

    public static void bindAccountSession(Context context, int accountId) {
        if (accountId2sessionMap.containsKey(accountId)) {
            // 这里是玩家重新建立链接
            // 业务逻辑可以不用改变，就是更改一个通讯的链接而已
        }
        accountId2sessionMap.put(accountId, context.getSession());
        session2AccountIdMap.put(context.getSession(), accountId);
    }

    public static int getAccountBySession(Session session) {
        if (session2AccountIdMap.containsKey(session)) {
            return session2AccountIdMap.get(session);
        }
        return -1;
    }

    private static void sendMessage(Session session, ProtobufMessage msg) {
        session.sendProtobufMessage(msg);
    }

    public static void sendMessage(int accountId, ProtobufMessage msg) {
        if (accountId2sessionMap.containsKey(accountId)) {
            sendMessage(accountId2sessionMap.get(accountId), msg);
        }
    }
}
