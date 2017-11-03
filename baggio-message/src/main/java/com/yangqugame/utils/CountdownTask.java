package com.yangqugame.utils;

import jazmin.core.Jazmin;

import java.util.concurrent.TimeUnit;

/**
 * Created by phiau on 2017/11/3 0003.
 */
public class CountdownTask {

    // 一次性的倒计时任务，单位秒
    public static void onlyOneTimeSeconds(Runnable runnable, long delay) {
        Jazmin.schedule(runnable, delay, TimeUnit.SECONDS);
    }
}
