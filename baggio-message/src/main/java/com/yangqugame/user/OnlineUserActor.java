package com.yangqugame.user;

import akka.actor.AbstractActor;
import akka.actor.Props;
import com.yangqugame.db.dao.data.TFootballerdataDao;
import com.yangqugame.db.dao.data.TLineupDao;
import com.yangqugame.db.dao.data.UserInfoDao;
import com.yangqugame.db.entry.data.TFootballerdata;
import com.yangqugame.db.entry.data.TLineup;
import com.yangqugame.db.entry.data.TMail;
import com.yangqugame.db.entry.data.UserInfo;
import com.yangqugame.global.TableConfigs;
import com.yangqugame.message.bean.LineupInfo;
import com.yangqugame.message.bean.PlayerInfo;
import com.yangqugame.message.bean.ReqCreateRole;
import com.yangqugame.message.bean.ResCreateRole;
import com.yangqugame.message.MessageSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  保存玩家的一些在线状态
 * Created by phiau on 2017/10/31 0031.
 */
public class OnlineUserActor extends AbstractActor {

    private long userId;
    public UserInfo userInfo;                        // 玩家信息
    private Map<Integer, TFootballerdata> playerMap;  // 球员信息
    private List<TLineup> lineups;                    // 阵容信息
    private List<TMail> mails;                        // 邮件信息
    public UserBackpack backpack;                     // 背包信息


    public static Props props(long userId) {
        return Props.create(OnlineUserActor.class, userId);
    }

    public OnlineUserActor(long userId) {
        this.userId = userId;
        userInfo = new UserInfoDao().query(userId);
        playerMap = new HashMap<>();
        List<TFootballerdata> players = new TFootballerdataDao().queryList(userId);
        if (null != players && 0 < players.size()) {
            for (TFootballerdata tmp : players) {
                playerMap.put(tmp.getRoleType(), tmp);
            }
            lineups = new TLineupDao().queryList(userId);
        }
        backpack = new UserBackpack(this, userId);
        sendBaseInfo2Client();
    }

    private void sendObj2Client(Object o) {
        MessageSender.sendByUserId(userId, o);
    }

    // 把玩家的一些基本信息通知客户单
    private void sendBaseInfo2Client() {
        // 玩家球员数据
        PlayerInfo playerInfo = new PlayerInfo();
        playerInfo.setNum(null == playerMap ? 0 : playerMap.size());
        if (0 < playerInfo.getNum()) {
            playerInfo.setPlayers(new ArrayList<>(playerMap.values()));
        }
        sendObj2Client(playerInfo);

        // 玩家阵容数据
        LineupInfo lineupInfo = new LineupInfo();
        lineupInfo.setNum(null == lineups ? 0 : lineups.size());
        lineupInfo.setLineups(lineups);
        sendObj2Client(lineupInfo);
    }

    private TFootballerdata initPlayer(int roleType, int fragmentNum) {
        TFootballerdata player = new TFootballerdata();
        player.setRoleType(roleType);
        player.setOwnerId(userId);
        player.setAwakenState(0);
        player.setFragment(fragmentNum);
        return player;
    }

    // 球员增加碎片
    public void addRoleFragment(int roleType, int num) {
        if (TableConfigs.existRoleType(roleType)) {
            TFootballerdata player = playerMap.get(roleType);
            if (null == player) {
                player = initPlayer(roleType, num);
                playerMap.put(roleType, player);
                // TODO：这里做成异步
                new TFootballerdataDao().insert(player);
            }
            if (0 < num) {
                player.addFragment(num);
            }
        }
    }

    // 球员增加经验
    public void addRoleExp(int roleType, int exp) {
        if (playerMap.containsKey(roleType)) {
            TFootballerdata player = playerMap.get(roleType);
            int lv = player.getLevel();
            int nextLvExpNeed = TableConfigs.getFootballerLevelNextExp(lv);
            exp += player.getExp();
            while (exp >= nextLvExpNeed) {
                exp -= nextLvExpNeed;
                lv++;
                nextLvExpNeed = TableConfigs.getFootballerLevelNextExp(lv);
            }
            player.setExp(exp);
            player.setLevel(lv);
        }
    }

    // 创建角色
    private void createRole(int roleType) {
        // 判断玩家有没有该角色，如果没有，判断是不是第一次配置的角色
        if (null == playerMap || 0 == playerMap.size()) {
            if (TableConfigs.existRoleTypeFirstLogin(roleType)) {
                if (TableConfigs.existRoleType(roleType)) {
                    // 初始化角色
                    addRoleFragment(roleType, 0);
                    ResCreateRole resCreateRole = new ResCreateRole();
                    resCreateRole.setSuccess(true);
                    resCreateRole.setNewRole(playerMap.get(roleType));
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
                .match(ReqCreateRole.class, req -> createRole(req.getRoleType()))
                .match(UserEvent.class, event -> handleEvent(event))
                .build();
    }
}
