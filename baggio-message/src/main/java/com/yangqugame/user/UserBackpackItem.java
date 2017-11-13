package com.yangqugame.user;

import com.yangqugame.db.entry.config.Item;
import com.yangqugame.db.entry.data.TFootballerdata;
import com.yangqugame.utils.StringUtils;

/**
 * Created by phiau on 2017/11/7 0007.
 */
public class UserBackpackItem {

    private final static short primary_category_currency = 1;        // 货币
    private final static short primary_category_item = 2;            // 道具
    private final static short primary_category_player_fragment = 3; // 球员碎片
    private final static short primary_category_spirit = 4;          // 精神石

    private final static short currency_user_exp = 1;           // 玩家经验
    private final static short currency_player_exp = 2;         // 球员经验
    private final static short currency_diamond = 3;            // 钻石
    private final static short currency_gold = 4;               // 金币
    private final static short currency_honor = 5;              // 荣誉点
    private final static short currency_st = 6;                 // ST/能量
    private final static short currency_bp = 7;                 // BP/战点

    private final static String vertical_line = "|";
    private final static String under_line = "_";

    private static void log(String msg) {
        System.out.println(msg);
    }

    /**
     * 道具使用效果，根据 useParameter 进行逻辑处理
     * useParameter 规则，一级分类、二级分类、数量，这个用下划线分割，多个用竖线分割
     * 一级分类（货币、道具、球员碎片、精神石）
     * 二级分类（货币：玩家经验、钻石... 道具：道具Id... ）
     * @param item 物品
     * @param times 使用物品数量
     * @param ext 额外参数，例如，球员 id
     */
    protected void itemAction(OnlineUserActor actor, Item item, int times, Object ext) {
        String param = item.getUseparameter();
        if (!StringUtils.isNullOrEmpty(param) && 0 < times) {
            parseItemParam(actor, item.getUseparameter(), times, ext);
        }
    }

    protected void parseItemParam(OnlineUserActor actor, String param, int times, Object ext) {
        String[] entry = param.split(vertical_line);
        if (null != entry && 0 < entry.length) {
            for (String e : entry) {
                String[] sub = e.split(under_line);
                if (null != sub && 3 == sub.length) {
                    short primary = Short.parseShort(sub[0]);
                    int id = Integer.parseInt(sub[1]);
                    int num = Integer.parseInt(sub[2]) * times;
                    switch (primary) {
                        case primary_category_currency:
                            paramCurrency(actor, (short) id, num, ext);
                            break;
                        case primary_category_item:
                            paramItem(actor, id, num);
                            break;
                        case primary_category_player_fragment:
                            actor.addRoleFragment(id, num);
                            break;
                        case primary_category_spirit:
                            paramSpirit(id, num);
                            break;
                        default:
                            log("道具使用参数配置错误，param ：" + param);
                            break;
                    }
                } else {
                    log("道具使用参数配置错误，param ：" + param);
                }
            }
        }
    }

    protected void paramItem(OnlineUserActor actor, int id, int itemNum) {
        actor.backpack.addItem(id, itemNum);
    }

    protected void paramSpirit(int id, int num) {
    }

    protected void paramCurrency(OnlineUserActor actor, short type, int num, Object ext) {
        switch (type) {
            case currency_user_exp:
                actor.userInfo.addExp(num);
                break;
            case currency_player_exp:
                if (null != ext && ext.getClass().equals(TFootballerdata.class)) {
                    actor.addRoleExp((Integer) ext, num);
                }
                break;
            case currency_diamond:
                actor.userInfo.addDiamond(num);
                break;
            case currency_gold:
                actor.userInfo.addGold(num);
                break;
            case currency_honor:
                actor.userInfo.addHonor(num);
                break;
            case currency_st:
                actor.userInfo.addST(num);
                break;
            case currency_bp:
                actor.userInfo.addBP(num);
                break;
            default:
                log("道具使用参数配置错误，没有对应的货币类型 ：" + type);
                break;
        }
    }


}
