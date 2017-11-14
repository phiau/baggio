package com.yangqugame.user;

import akka.actor.AbstractActor;
import akka.actor.Props;
import com.yangqugame.db.dao.data.TFootballerdataDao;
import com.yangqugame.db.dao.data.TLineupDao;
import com.yangqugame.db.dao.data.UserInfoDao;
import com.yangqugame.db.entry.data.*;
import com.yangqugame.global.TableConfigs;
import com.yangqugame.message.bean.*;
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
    }

    private void login() {
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
    private void sendLineupInfo2Client() {
        LineupInfo lineupInfo = new LineupInfo();
        lineupInfo.setNum(0);
        if (null != lineups && 0 < lineups.size()) {
            lineupInfo.setNum(lineups.size());
            lineupInfo.setLineups(lineups);
        }
        sendObj2Client(lineupInfo);
    }
    // 背包信息
    private void sendBackpackInfo2Client() {
        BackpackInfo packInfo = new BackpackInfo();
        List<TBackpackItem> backpackItems = backpack.getItems();
        if (null != backpackItems && 0 < backpackItems.size()) {
            packInfo.setNum(backpackItems.size());
            packInfo.setItems(backpackItems);
        }
        sendObj2Client(packInfo);
    }
    // 邮件信息
    private void sendMailInfo2Client() {
        MailInfo mailInfo = new MailInfo();
        if (null != mails && 0 < mails.size()) {
            mailInfo.setMails(mails);
        }
//        sendObj2Client(mails);
    }
    private void sendBaseInfo2Client() {
        // 玩家球员数据
        PlayerInfo playerInfo = new PlayerInfo();
        playerInfo.setNum(null == playerMap ? 0 : playerMap.size());
        if (0 < playerInfo.getNum()) {
            playerInfo.setPlayers(new ArrayList<>(playerMap.values()));
        } else {
            playerInfo.setPlayers(new ArrayList<>());
        }
        sendObj2Client(playerInfo);
        // 玩家阵容数据
        sendLineupInfo2Client();
        // 玩家背包数据
        sendBackpackInfo2Client();
        // 玩家邮件数据
        sendMailInfo2Client();
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

    // 创建第一个球员
    private void createRole(int roleType) {
        System.out.println("==============================create Role");
        // 判断玩家有没有该球员，如果没有，判断是不是第一次配置的球员
        if (null == playerMap || 0 == playerMap.size()) {
            if (TableConfigs.existRoleTypeFirstLogin(roleType)) {
                if (TableConfigs.existRoleType(roleType)) {
                    // 初始化球员
                    addRoleFragment(roleType, 0);
                    ResCreateRole resCreateRole = new ResCreateRole();
                    resCreateRole.setSuccess(true);
                    resCreateRole.setNewRole(playerMap.get(roleType));
                    sendObj2Client(resCreateRole);

                    // 第一个球员，初始化五套阵容阵容，设置为队长，位置是 2
                    lineups = new ArrayList<>();
                    TLineupDao tLineupDao = new TLineupDao();
                    for(int i=1; i<=UserLineupConst.USER_LINEUP_NUM; i++) {
                        TLineup lineup = new TLineup();
                        lineup.setOwnerid(userId);
                        lineup.setLineupId(i);
                        lineup.setAce(UserLineupConst.USER_LINUP_ACE_DEFAULT);
                        lineup.setPositions(UserLineupConst.USER_LINUP_ACE_DEFAULT, roleType);
                        tLineupDao.insert(lineup);

                        lineups.add(i-1, lineup);
                    }
                    sendLineupInfo2Client();
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
                login();
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
                .matchAny((obj) -> {System.out.println("online user actor default message");})
                .build();
    }
}
