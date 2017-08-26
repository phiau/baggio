package com.yangqugame.message;

import jazmin.server.protobuf.ProtobufMessage;
import jazmin.server.protobuf.client.ProtobufMessageClient;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

/**
 * Created by ging on 27/08/2017.
 * baggio
 */

public class BaggioProtobufInvokeServiceTest {

    @Test
    public void testInvokerService() {
        ProtobufMessageClient client = new ProtobufMessageClient();
        client.connect("127.0.0.1", 2001);
        ProtobufMessage msg = client.invokeSync(10001, "Hello".getBytes());
        MatcherAssert.assertThat(msg.getId(), CoreMatchers.equalTo(10002));
    }

}
