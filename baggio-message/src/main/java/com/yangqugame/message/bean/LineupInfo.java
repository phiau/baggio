package com.yangqugame.message.bean;

import com.yangqugame.db.entry.data.TLineup;
import com.yangqugame.message.annotation.Proto;
import x1.proto.pb.BaseInfo;

import java.util.List;

/**
 * Created by phiau on 2017/11/1 0001.
 */
@Proto(code = 103101, message = BaseInfo.LineupInfoMessage.class)
public class LineupInfo {

    private int num;
    private int defaultId;
    private List<TLineup> lineups;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getDefaultId() {
        return defaultId;
    }

    public void setDefaultId(int defaultId) {
        this.defaultId = defaultId;
    }

    public List<TLineup> getLineups() {
        return lineups;
    }

    public void setLineups(List<TLineup> lineups) {
        this.lineups = lineups;
    }
}
