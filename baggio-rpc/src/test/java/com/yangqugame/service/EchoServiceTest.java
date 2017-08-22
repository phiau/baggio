package com.yangqugame.service;

import jazmin.core.Jazmin;
import jazmin.driver.rpc.JazminRpcDriver;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.net.URISyntaxException;

/**
 * Created by ging on 05/07/2017.
 * baggio
 */

public class EchoServiceTest {

    @Test
    public void tesEchoMessage() throws URISyntaxException {
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
        EchoService echoService = driver.create(EchoService.class, "cluster");
        String msg = echoService.echo("message");
        MatcherAssert.assertThat(msg, CoreMatchers.equalTo("MESSAGE"));
    }

}
