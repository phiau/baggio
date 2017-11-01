package com.yangqugame.user;

import akka.actor.ActorRef;
import com.yangqugame.global.ServerActorManager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by phiau on 2017/10/31 0031.
 */
public class OnlineUserActorManager {

    private static ConcurrentMap<Long, ActorRef> userActorMap = new ConcurrentHashMap<>();   // key:userId

    public static void userLogin(long userId) {
        ActorRef actorRef = ServerActorManager.newOnlineUserActor(userId);
        userActorMap.put(userId, actorRef);
    }

    public static ActorRef getActorRef(long userId) {
        return userActorMap.get(userId);
    }

    public static void tellActorByUserId(long userId, Object o) {
        if (userActorMap.containsKey(userId)) {
            userActorMap.get(userId).tell(o, ActorRef.noSender());
        }
    }
}
