package com.yangqugame.message.bean;

import com.yangqugame.db.entry.data.TFootballerdata;
import com.yangqugame.message.annotation.Proto;
import x1.proto.pb.BaseInfo;

import java.util.List;

/**
 * Created by phiau on 2017/11/1 0001.
 */
@Proto(code = 103100, message = BaseInfo.PlayerInfoMessage.class)
public class PlayerInfo {

    private int num;
    private List<TFootballerdata> players;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<TFootballerdata> getPlayers() {
        return players;
    }

    public void setPlayers(List<TFootballerdata> players) {
        this.players = players;
    }
}
