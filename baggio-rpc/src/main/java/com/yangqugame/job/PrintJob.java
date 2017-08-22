package com.yangqugame.job;

import jazmin.core.job.JobDefine;

/**
 * Created by ging on 22/08/2017.
 * baggio
 */

public class PrintJob {

    @JobDefine(cron="0 * * * * ?")
    public void cleanResource() {
        System.out.println("clean resource job ");
    }

}
