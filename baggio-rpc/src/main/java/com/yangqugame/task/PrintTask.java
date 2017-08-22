package com.yangqugame.task;

import jazmin.core.task.TaskDefine;

import java.util.concurrent.TimeUnit;

/**
 * Created by ging on 22/08/2017.
 * baggio
 */

public class PrintTask {

    @TaskDefine(initialDelay=10,period=10,unit= TimeUnit.SECONDS)
    public void giftTask(){
        System.out.println("gift task");
    }

}
