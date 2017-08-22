package com.yangqugame.message;

import jazmin.server.msg.client.MessageClient;
import jazmin.server.msg.codec.ResponseMessage;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Created by ging on 07/07/2017.
 * baggio
 */

public class EchoTest {


    @Test
    public void echo() {
        MessageClient client = new MessageClient();
        client.connect("127.0.0.1", 3001);
        ResponseMessage responseMessage = client.invokeSync("EchoMessage.echo", new String[]{"hello"});
        MatcherAssert.assertThat(responseMessage.responseObject.toString(), CoreMatchers.containsString("echo:"));
    }

}
