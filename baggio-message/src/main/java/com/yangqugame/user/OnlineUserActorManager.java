package com.yangqugame.user;

import akka.actor.ActorRef;
import com.yangqugame.global.ServerActorManager;
import com.yangqugame.utils.CountdownTask;
import io.netty.util.internal.ConcurrentSet;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by phiau on 2017/10/31 0031.
 */
public class OnlineUserActorManager {

    private static ConcurrentSet<Long> disconnectUser = new ConcurrentSet<>();  // 玩家一当断线，就做个标记，一定时间之后，再执行退出逻辑
    private static ConcurrentMap<Long, ActorRef> userActorMap = new ConcurrentHashMap<>();   // key:userId

    // 登录
    public static void userLogin(long userId) {
        ActorRef actorRef = ServerActorManager.newOnlineUserActor(userId);
        userActorMap.put(userId, actorRef);
        tellActorByUserId(userId, new UserEvent(UserEventConst.USER_EVENT_LOGINED));
        disconnectUser.remove(userId);
    }

    // 客户端请求退出
    public static void userLogout(long userId) {
        ActorRef actorRef = userActorMap.remove(userId);
        actorRef.tell(new UserEvent(UserEventConst.USER_EVENT_LOGOUT), ActorRef.noSender());
    }

    // 断线后，保护时间到了，如果还在标记集合里面，执行相应逻辑
    public static void userDisconnetTimeOut(long userId) {
        if (disconnectUser.contains(userId)) {
            userLogout(userId);
            disconnectUser.remove(userId);
        }
    }

    // 玩家掉线
    public static void userDisconnect(long userId) {
        if (!disconnectUser.contains(userId)) {
            disconnectUser.add(userId);
            CountdownTask.onlyOneTimeSeconds(new UserDisconnectTask(userId), 20);
        }
    }

    public static void tellActorByUserId(long userId, Object o) {
        if (userActorMap.containsKey(userId)) {
            userActorMap.get(userId).tell(o, ActorRef.noSender());
        }
    }

    public static class UserDisconnectTask implements Runnable {
        private long userId;
        public UserDisconnectTask(long userId) { this.userId = userId; }
        @Override
        public void run() { OnlineUserActorManager.userDisconnetTimeOut(userId); }
    }
}