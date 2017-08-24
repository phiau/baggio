package com.yangqugame.message;

import jazmin.server.msg.Context;

/**
 * Created by Administrator on 2017/8/24 0024.
 */
public class MathMessage {

    public void add(Context context, Integer a, Integer b) {
        context.ret(a + b);
    }
}
