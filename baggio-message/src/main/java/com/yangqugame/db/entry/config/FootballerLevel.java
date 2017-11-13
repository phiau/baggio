package com.yangqugame.db.entry.config;

/**
 * Created by phiau on 2017/11/6 0006.
 */
public class FootballerLevel {

    private int level;      // 球员等级
    private int nextNeed;   // 提升到下一级需要的经验

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getNextNeed() {
        return nextNeed;
    }

    public void setNextNeed(int nextNeed) {
        this.nextNeed = nextNeed;
    }
}
