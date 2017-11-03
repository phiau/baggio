package com.yangqugame.message;

import com.yangqugame.user.OnlineUserActorManager;
import com.yangqugame.user.UserEvent;
import com.yangqugame.user.UserEventConst;
import com.yangqugame.user.UserManager;
import com.yangqugame.utils.StringUtils;
import jazmin.core.Jazmin;
import jazmin.server.msg.Session;
import jazmin.server.msg.SessionLifecycleListener;
import jazmin.server.protobuf.Context;
import jazmin.server.protobuf.ProtobufMessage;
import jazmin.server.protobuf.ProtobufServer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by phiau on 2017/9/11 0011.
 */
public class SessionManager {

    private static Map<Integer, Session> accountId2sessionMap = new HashMap<>();    // key:account id, value:session
    private static Map<Session, Integer> session2AccountIdMap = new HashMap<>();    // key:session, value:account id

    private static SessionLifecycleListener sessionLifecycleListener = new SessionLifecycleListener() {
        @Override
        public void sessionCreated(Session session) throws Exception {
            System.out.println("----------- sessionCreated -----------");
        }

        @Override
        public void sessionDisconnected(Session session) throws Exception {
            String principal = session.getPrincipal();
            if (!StringUtils.isNullOrEmpty(principal)) {
                int accountId = Integer.parseInt(principal);
                long userId = UserManager.getUserIdByAccountId(accountId);
                OnlineUserActorManager.userDisconnect(userId);
            }
        }
    };

    public static void bindAccountSession(Context context, int accountId) {
        if (accountId2sessionMap.containsKey(accountId)) {
            // 这里是玩家重新建立链接
            // 业务逻辑可以不用改变，就是更改一个通讯的链接而已
        }
        accountId2sessionMap.put(accountId, context.getSession());
        session2AccountIdMap.put(context.getSession(), accountId);
        ProtobufServer server = Jazmin.getServer(ProtobufServer.class);
        server.setPrincipal(context.getSession(), "" + accountId, "");
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

    public static SessionLifecycleListener getSessionLifecycleListener() {
        return sessionLifecycleListener;
    }
}
