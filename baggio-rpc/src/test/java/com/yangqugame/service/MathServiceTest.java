package com.yangqugame.service;

import jazmin.core.Jazmin;
import jazmin.driver.rpc.JazminRpcDriver;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.net.URISyntaxException;

/**
 * Created by Administrator on 2017/8/22 0022.
 */
public class MathServiceTest {

    @Test
    public void tesMathMessage() throws URISyntaxException {
        JazminRpcDriver driver = null;
        if (Jazmin.getDriver(JazminRpcDriver.class) == null) {
            driver = new JazminRpcDriver();
            driver.addRemoteServer("jazmin://127.0.0.1:6001/cluster/rpc");
            Jazmin.addDriver(driver);
            Jazmin.start();
        }
        else {
            driver = Jazmin.getDriver(JazminRpcDriver.class);
        }
        MathService mathService = driver.create(MathService.class, "cluster");
        int sum = mathService.add(1, 2);
        MatcherAssert.assertThat(sum, CoreMatchers.equalTo(3));
    }
}