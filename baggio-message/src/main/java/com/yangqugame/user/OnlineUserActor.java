package com.yangqugame.user;

import akka.actor.AbstractActor;
import akka.actor.Props;
import com.yangqugame.db.dao.data.TFootballerdataDao;
import com.yangqugame.db.dao.data.TLineupDao;
import com.yangqugame.db.entry.data.TFootballerdata;
import com.yangqugame.db.entry.data.TLineup;
import com.yangqugame.global.TableConfigs;
import com.yangqugame.message.bean.LineupInfo;
import com.yangqugame.message.bean.PlayerInfo;
import com.yangqugame.message.bean.ReqCreateRole;
import com.yangqugame.message.bean.ResCreateRole;
import com.yangqugame.message.MessageSender;

import java.util.List;

/**
 *  保存玩家的一些在线状态
 * Created by phiau on 2017/10/31 0031.
 */
public class OnlineUserActor extends AbstractActor {

    private long userId;
    private List<TFootballerdata> players;   // 球员信息
    private List<TLineup> lineups;           // 阵容信息
    private UserBackpack backpack;           // 背包信息

    public static Props props(long userId) {
        return Props.create(OnlineUserActor.class, userId);
    }

    public OnlineUserActor(long userId) {
        this.userId = userId;
        players = new TFootballerdataDao().queryList(userId);
        if (null != players && 0 < players.size()) {
            lineups = new TLineupDao().queryList(userId);
        }
        backpack = new UserBackpack(userId);
        sendBaseInfo2Client();
    }

    private void sendObj2Client(Object o) {
        MessageSender.sendByUserId(userId, o);
    }

    // 把玩家的一些基本信息通知客户单
    private void sendBaseInfo2Client() {
        // 玩家球员数据
        PlayerInfo playerInfo = new PlayerInfo();
        playerInfo.setNum(null == players ? 0 : players.size());
        playerInfo.setPlayers(players);
        sendObj2Client(playerInfo);

        // 玩家阵容数据
        LineupInfo lineupInfo = new LineupInfo();
        lineupInfo.setNum(null == lineups ? 0 : lineups.size());
        lineupInfo.setLineups(lineups);
        sendObj2Client(lineupInfo);
    }

    private TFootballerdata initPlayer(int roleType) {
        TFootballerdata player = new TFootballerdata();
        player.setRoleType(roleType);
        player.setOwnerId(userId);
        player.setAwakenState(0);
        return player;
    }

    // 创建角色
    private void createRole(ReqCreateRole req) {
        // 判断玩家有没有该角色，如果没有，判断是不是第一次配置的角色
        int roleType = req.getRoleType();
        if (null == players || 0 == players.size()) {
            if (TableConfigs.existRoleTypeFirstLogin(roleType)) {
                if (TableConfigs.existRoleType(roleType)) {
                    // 初始化角色
                    TFootballerdata player = initPlayer(roleType);
                    // TODO：这里做成异步
                    new TFootballerdataDao().insert(player);
                    players.add(player);
                    ResCreateRole resCreateRole = new ResCreateRole();
                    resCreateRole.setSuccess(true);
                    resCreateRole.setNewRole(player);
                    sendObj2Client(resCreateRole);
                }
            }
        }
    }

    private void tip(String msg) {
        return;
    }

    private void handleEvent(UserEvent event) {
        switch (event.getEvent()) {
            case UserEventConst.USER_EVENT_LOGINED:
                System.out.println(String.format("========================== 玩家 %d 登录成功 ==========================", userId));
                break;
            case UserEventConst.USER_EVENT_LOGOUT:
                System.out.println(String.format("========================== 玩家 %d 退出成功 ==========================", userId));
                break;
            default:
                break;
        }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(ReqCreateRole.class, req -> createRole(req))
                .match(UserEvent.class, event -> handleEvent(event))
                .build();
    }
}
