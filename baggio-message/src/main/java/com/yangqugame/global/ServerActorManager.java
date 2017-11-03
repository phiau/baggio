package com.yangqugame.global;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.yangqugame.user.OnlineUserActor;

/**
 * Created by phiau on 2017/10/31 0031.
 */
public class ServerActorManager {

    private final static ActorSystem actorSystem = ActorSystem.create("baggio-server");

    public static synchronized ActorRef newOnlineUserActor(long userId) {
        return actorSystem.actorOf(OnlineUserActor.props(userId));
    }
}
