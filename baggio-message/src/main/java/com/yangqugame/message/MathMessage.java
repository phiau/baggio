package com.yangqugame.message;

import jazmin.server.msg.Context;

/**
 * Created by Administrator on 2017/8/24 0024.
 */
public class MathMessage {

    public static class MathItem {
        public int num1;
        public int num2;
        public int sum;
    }

    public void add(Context context, MathItem mathItem) {
        mathItem.sum = mathItem.num1 + mathItem.num2;
        context.ret(mathItem);
    }

    public void add(Context context, int a, int b) {
        context.ret(a + b);
    }
}
